package com.wxmimperio.txt2sequencefile;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.*;
import org.apache.hadoop.ipc.RemoteException;
import org.apache.hadoop.util.ReflectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.lang.reflect.Method;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by weiximing.imperio on 2016/7/25.
 */
public class HDFSRunner {
    private static final Logger LOG = LoggerFactory.getLogger(HDFSRunner.class);
    private static final String hdfsUri = PropManager.getInstance().getPropertyByString("hdfs.uri");
    private static final String DEFAULTFS = "fs.defaultFS";
    private static final String DFS_FAILURE_ENABLE = "dfs.client.block.write.replace-datanode-on-failure.enable";
    private static final String DFS_FAILURE_POLICY = "dfs.client.block.write.replace-datanode-on-failure.policy";
    private static final String DFS_SESSION_TIMEOUT = "dfs.client.socket-timeout";
    private static final String DFS_TRANSFER_THREADS = "dfs.datanode.max.transfer.threads";
    private static final String DFS_SUPPORT_APPEND = "dfs.support.append";
    private static final String CORE_SITE_XML = "core-site.xml";
    private static final String HDFS_SITE_XML = "hdfs-site.xml";
    private static final String MAPRED_SITE_XML = "mapred-site.xml";
    private static final String YARN_SITE_XML = "mapred-site.xml";


    static Configuration config() {
        Configuration conf = new Configuration();
        conf.addResource(CORE_SITE_XML);
        conf.addResource(HDFS_SITE_XML);
        conf.addResource(MAPRED_SITE_XML);
        conf.addResource(YARN_SITE_XML);
        conf.setBoolean(DFS_SUPPORT_APPEND, true);
        conf.setBoolean(DFS_FAILURE_ENABLE, true);
        conf.set(DEFAULTFS, hdfsUri);
        conf.set(DFS_FAILURE_POLICY, "NEVER");
        //conf.set(DFS_SESSION_TIMEOUT, "180000");
        conf.set(DFS_TRANSFER_THREADS, "16000");
        return conf;
    }

    public static synchronized boolean append2SequenceFile(String filePath, List<String> buffer) {
        boolean flags = false;
        FSDataOutputStream outStream = null;
        FileSystem hdfs;
        SequenceFile.Writer writer = null;
        Method refHflushOrSync;
        try {
            Path path = new Path(filePath);
            hdfs = FileSystem.get(URI.create(hdfsUri), config());
            if (hdfs.isFile(path)) {
                outStream = hdfs.append(path);
            } else {
                outStream = hdfs.create(path);
            }
            Text value = new Text();
            BytesWritable key = new BytesWritable();
            BytesWritable EMPTY_KEY = new BytesWritable();

            writer = SequenceFile.createWriter(
                    config(),
                    outStream,
                    key.getClass(),
                    value.getClass(),
                    SequenceFile.CompressionType.BLOCK,
                    null);

            int index = 0;
            for (String str : buffer) {
                value.set(str);
                writer.append(EMPTY_KEY, value);
                index++;
            }
            writer.sync();
            refHflushOrSync = reflectHflushOrSync(outStream);
            refHflushOrSync.invoke(outStream);

            System.out.println("write size=" + index);
            flags = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeStream(writer);
            IOUtils.closeStream(outStream);
        }
        return flags;
    }

    private static Method reflectHflushOrSync(FSDataOutputStream os) {
        Method m = null;
        if (os != null) {
            Class<?> fsDataOutputStreamClass = os.getClass();
            try {
                m = fsDataOutputStreamClass.getMethod("hflush");
            } catch (NoSuchMethodException ex) {
                ex.printStackTrace();
                try {
                    m = fsDataOutputStreamClass.getMethod("sync");
                } catch (Exception ex1) {
                    String msg = "Neither hflush not sync were found. That seems to be " +
                            "a problem!";
                    ex1.printStackTrace();
                }
            }
        }
        return m;
    }


    /**
     * @param filePath
     * @param buffer
     */
    public static synchronized boolean appendFile(String filePath, List<String> buffer) {
        BufferedWriter writer = null;
        FSDataOutputStream output = null;
        FileSystem fs = null;
        boolean writeDone = true;
        StringBuffer stringBuffer = new StringBuffer();

        for (String msg : buffer) {
            stringBuffer.append(msg).append("\n");
        }

        try {
            fs = FileSystem.get(URI.create(hdfsUri), config());
            Path path = new Path(filePath);
            if (!fs.exists(path)) {
                output = fs.create(path);
                LOG.warn("File " + filePath + " is not exist!");
            } else {
                output = fs.append(path);
            }
            writer = new BufferedWriter(new OutputStreamWriter(output, "UTF-8"));
            writer.write(stringBuffer.toString());
            writer.flush();
        } catch (RemoteException e) {
            writeDone = false;
            //java.io.InterruptedIOException: Interruped while waiting for IO on channel java.nio.channels.SocketChannel
            closeFSDataOutputStream(output);
            closeBufferedWriter(writer);
            LOG.warn("RemoteException happened warn ", e);
        } catch (IOException e) {
            writeDone = false;
            closeFSDataOutputStream(output);
            closeBufferedWriter(writer);
            LOG.error("Create or Append HDFS files happened error ", e);
        } finally {
            closeFSDataOutputStream(output);
            closeBufferedWriter(writer);
        }
        return writeDone;
    }

    /**
     * @param filePath
     * @return
     */
    public static List<String> fileReader(String filePath) {
        List<String> lines = new ArrayList<>();
        BufferedReader br = null;
        FileSystem fs = null;
        Path path = new Path(filePath);
        try {
            fs = FileSystem.get(URI.create(hdfsUri), config());
            if (fs.exists(path)) {
                FSDataInputStream inputStream = fs.open(path);
                br = new BufferedReader(new InputStreamReader(inputStream));

                String line = null;
                while (null != (line = br.readLine())) {
                    lines.add(line);
                }
            }
        } catch (IOException e) {
            LOG.error("Read file happened error ", e);
        } finally {
            IOUtils.closeStream(br);
        }

        return lines;
    }

    /**
     * @param filePath
     * @param data
     * @return
     */
    public static synchronized void sequenceFileWrite(String filePath, List<String> data) {
        SequenceFile.Writer writer = null;

        try {
            Text value = new Text();
            BytesWritable EMPTY_KEY = new BytesWritable();

            Path path = new Path(filePath);
            BytesWritable key = new BytesWritable();

            writer = SequenceFile.createWriter(
                    config(),
                    SequenceFile.Writer.file(path),
                    SequenceFile.Writer.keyClass(key.getClass()),
                    SequenceFile.Writer.valueClass(value.getClass()),
                    //In hadoop-2.6.0-cdh5, it can use hadoop-common-2.6.5 with appendIfExists()
                    //SequenceFile.Writer.appendIfExists(true),
                    SequenceFile.Writer.compression(SequenceFile.CompressionType.BLOCK)
            );

            int index = 0;
            for (String str : data) {
                value.set(str);
                writer.append(EMPTY_KEY, value);
                index++;
            }
            System.out.println("write size=" + index);
        } catch (RemoteException e) {
            LOG.warn("RemoteException happened warn ", e);
        } catch (IOException e) {
            LOG.error("Create or Append SequenceFile happened error ", e);
        } finally {
            IOUtils.closeStream(writer);
        }
    }

    /**
     * @param filePath
     * @return
     */
    public static List<String> sequenceFileReader(String filePath) {
        List<String> messages = new ArrayList<>();

        Path path = new Path(filePath);
        SequenceFile.Reader reader = null;
        FileSystem fs = null;
        try {
            fs = FileSystem.get(URI.create(hdfsUri), config());
            if (fs.exists(path)) {
                reader = new SequenceFile.Reader(config(), SequenceFile.Reader.file(path));
                Writable key = (Writable) ReflectionUtils.newInstance(
                        reader.getKeyClass(), config());
                Writable value = (Writable) ReflectionUtils.newInstance(
                        reader.getValueClass(), config());
                while (reader.next(key, value)) {
                    messages.add(value.toString());
                }
            }
        } catch (Exception e) {
            LOG.error("Read SequenceFile happened error ", e);
        } finally {
            IOUtils.closeStream(reader);
        }
        return messages;
    }

    /**
     * @param filePath
     */
    public static synchronized void deleteFile(String filePath) {
        FileSystem fs = null;
        try {
            fs = FileSystem.get(URI.create(hdfsUri), config());
            fs.delete(new Path(filePath), true);
        } catch (Exception e) {
            closeFileSystem(fs);
            LOG.error(String.format("Delete file %s error ", filePath), e);
        }
    }

    /**
     * @param txtPath
     * @param sequencePath
     */
    public static void txtFile2SequenceFile(String txtPath, String sequencePath) {
        sequenceFileWrite(sequencePath, fileReader(txtPath));
        //sequenceFileReader(sequencePath);
    }

    /**
     * @param filePath
     * @return
     */
    private static void createFile(String filePath) {
        Path path = new Path(filePath);
        FileSystem fs = getNewFs();
        try {
            if (!fs.exists(path)) {
                fs.create(path).close();
            }
        } catch (IOException e) {
            closeFileSystem(fs);
            LOG.error("Create HDFS files happened error : ", e);
        }
    }


    /**
     * @param fs
     */
    private static void closeFileSystem(FileSystem fs) {
        try {
            if (fs != null) {
                fs.close();
                fs = null;
                LOG.info("FileSystem closed!!");
            }
        } catch (IOException e) {
            LOG.error("Close HDFS FileSystem happened error ", e);
        }
    }

    /**
     * @return
     */
    private static FileSystem getNewFs() {
        FileSystem fs = null;
        try {
            fs = FileSystem.get(URI.create(hdfsUri), config());
        } catch (IOException e) {
            closeFileSystem(fs);
            LOG.error("Get NewFs happened error ", e);
        }
        return fs;
    }

    /**
     * 关闭 BufferedWriter
     *
     * @param br
     */
    private static void closeBufferedWriter(BufferedWriter br) {
        try {
            if (br != null) {
                br.close();
            }
        } catch (IOException e) {
            LOG.error("Close BufferedWriter happened error" + e);
        }
    }

    /**
     * 关闭Fds流
     *
     * @param fsos
     */
    private static void closeFSDataOutputStream(FSDataOutputStream fsos) {
        try {
            if (fsos != null) {
                fsos.close();
            }
        } catch (IOException e) {
            LOG.error("Close FSDataOutputStream happened error" + e);
        }
    }
}
