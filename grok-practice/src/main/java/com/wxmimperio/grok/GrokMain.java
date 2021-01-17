package com.wxmimperio.grok;

import io.krakens.grok.api.Grok;
import io.krakens.grok.api.GrokCompiler;
import io.krakens.grok.api.Match;

import java.util.HashMap;
import java.util.Map;

public class GrokMain {

    public static void main(String[] args) {
        /* Create a new grokCompiler instance */
        GrokCompiler grokCompiler = GrokCompiler.newInstance();
        grokCompiler.registerDefaultPatterns();

        /* Grok pattern to compile, here httpd logs */
        final Grok grok = grokCompiler.compile("%{COMBINEDAPACHELOG}");

        /* Line of log to match */
        String log = "112.169.19.192 - - [06/Mar/2013:01:36:30 +0900] \"GET / HTTP/1.1\" 200 44346 \"-\" \"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_8_2) AppleWebKit/537.22 (KHTML, like Gecko) Chrome/25.0.1364.152 Safari/537.22\"";

        Match gm = grok.match(log);

        /* Get the map with matches */
        final Map<String, Object> capture = gm.capture();

        System.out.println(capture);

        String log1 = "java.lang.NoSuchMethodError: org.springframework.util.Assert.notNull(Ljava/lang/Object;Ljava/util/function/Supplier;)V\n" +
                "\tat org.springframework.test.context.support.ContextLoaderUtils.resolveContextConfigurationAttributes(ContextLoaderUtils.java:240)\n" +
                "\tat org.springframework.test.context.support.AbstractTestContextBootstrapper.buildMergedContextConfiguration(AbstractTestContextBootstrapper.java:295)\n" +
                "\tat org.springframework.test.context.support.AbstractTestContextBootstrapper.buildTestContext(AbstractTestContextBootstrapper.java:108)\n" +
                "\tat org.springframework.test.context.TestContextManager.<init>(TestContextManager.java:139)\n" +
                "\tat org.springframework.test.context.TestContextManager.<init>(TestContextManager.java:124)\n" +
                "\tat org.springframework.test.context.junit4.SpringJUnit4ClassRunner.createTestContextManager(SpringJUnit4ClassRunner.java:151)\n" +
                "\tat org.springframework.test.context.junit4.SpringJUnit4ClassRunner.<init>(SpringJUnit4ClassRunner.java:142)\n" +
                "\tat sun.reflect.NativeConstructorAccessorImpl.newInstance0(Native Method)\n" +
                "\tat sun.reflect.NativeConstructorAccessorImpl.newInstance(Unknown Source)\n" +
                "\tat sun.reflect.DelegatingConstructorAccessorImpl.newInstance(Unknown Source)\n" +
                "\tat java.lang.reflect.Constructor.newInstance(Unknown Source)\n" +
                "\tat org.junit.internal.builders.AnnotatedBuilder.buildRunner(AnnotatedBuilder.java:104)\n" +
                "\tat org.junit.internal.builders.AnnotatedBuilder.runnerForClass(AnnotatedBuilder.java:86)\n" +
                "\tat org.junit.runners.model.RunnerBuilder.safeRunnerForClass(RunnerBuilder.java:59)\n" +
                "\tat org.junit.internal.builders.AllDefaultPossibilitiesBuilder.runnerForClass(AllDefaultPossibilitiesBuilder.java:26)\n" +
                "\tat org.junit.runners.model.RunnerBuilder.safeRunnerForClass(RunnerBuilder.java:59)\n" +
                "\tat org.junit.internal.requests.ClassRequest.getRunner(ClassRequest.java:33)\n" +
                "\tat org.eclipse.jdt.internal.junit4.runner.JUnit4TestLoader.createUnfilteredTest(JUnit4TestLoader.java:84)\n" +
                "\tat org.eclipse.jdt.internal.junit4.runner.JUnit4TestLoader.createTest(JUnit4TestLoader.java:70)\n" +
                "\tat org.eclipse.jdt.internal.junit4.runner.JUnit4TestLoader.loadTests(JUnit4TestLoader.java:43)\n" +
                "\tat org.eclipse.jdt.internal.junit.runner.RemoteTestRunner.runTests(RemoteTestRunner.java:444)\n" +
                "\tat org.eclipse.jdt.internal.junit.runner.RemoteTestRunner.runTests(RemoteTestRunner.java:678)\n" +
                "\tat org.eclipse.jdt.internal.junit.runner.RemoteTestRunner.run(RemoteTestRunner.java:382)\n" +
                "\tat org.eclipse.jdt.internal.junit.runner.RemoteTestRunner.main(RemoteTestRunner.java:192)";

        Map<String, String> p = new HashMap<>();
        p.put("JAVACLASS", "(?:[a-zA-Z0-9-]+\\.)+[A-Za-z0-9$_]+");
        p.put("JAVAFILE", "(?:[A-Za-z0-9_. -]+)");
        p.put("JAVASTACKTRACEPART", "at %{JAVACLASS:class}\\.%{WORD:method}\\(%{JAVAFILE:file}:%{NUMBER:line}\\)");
        grokCompiler.register(p);
        final Grok grok1 = grokCompiler.compile("%{JAVACLASS:class}");

        Match gm1 = grok1.match(log1);

        /* Get the map with matches */
        final Map<String, Object> capture1 = gm1.capture();

        System.out.println(capture1);
    }
}
