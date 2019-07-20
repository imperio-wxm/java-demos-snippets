package com.wxmimperio.shell.service;

import com.wxmimperio.shell.util.CommandUtils;
import com.wxmimperio.shell.util.ShellUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class ShellService {

    private static final Logger LOG = LoggerFactory.getLogger(ShellService.class);

    @Scheduled(cron = "* */10 * * * ?")
    public void runShell() {
        LOG.info("Starting.....");
        long start = System.currentTimeMillis();
        List<String> tableName = Arrays.asList("swy_login_device_bitmap_mid, wol3d_login_bitmap_mid, cake_login_bitmap_mid, fsol_login_bitmap_mid, cqsjw_login_bitmap_mid, vc_login_bitmap_mid, dbcq_character_deposit_bitmap_mid, yc_login_bitmap_mid, mirh5ys_login_bitmap_mid, ahrx_login_bitmap_mid, rwby_login_bitmap_mid, mygj_character_deposit_bitmap_mid, gmysap_login_bitmap_mid, woool_character_login_bitmap_mid, swy_login_bitmap_mid, akb48_login_bitmap_mid, ahrx_character_deposit_bitmap_mid, gmys_character_deposit_bitmap_mid, mygj_login_bitmap_mid, gmys_login_bitmap_mid, crossgate_login_bitmap_mid, gmyskr_login_bitmap_mid, fsol_character_deposit_bitmap_mid, dbcq_login_bitmap_mid, ks_login_bitmap_mid".split(",", -1));
        tableName.forEach(name -> {
            String command = CommandUtils.getCommand(name.trim().toLowerCase());
            ShellUtils.callShell(command);
            LOG.info(String.format("Finished %s cost %s ms", name, (System.currentTimeMillis() - start)));
        });
        LOG.info(String.format("Finished all cost %s ms", (System.currentTimeMillis() - start)));
    }
}
