package com.wxmimperio.txt2sequencefile;

import org.apache.hadoop.fs.FsUrlStreamHandlerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by weiximing.imperio on 2017/4/12.
 */
public class TranMain {
    private static final Logger LOG = LoggerFactory.getLogger(TranMain.class);

    public static void main(String[] args) {

        //String[] textName = PropManager.getInstance().getPropertyByString("text.files").split(",", -1);
        //String[] sequenceName = PropManager.getInstance().getPropertyByString("sequence.files").split(",", -1);

        //String[] textName = "glog_fsbns_city,glog_fsbns_cityreset,glog_fsbns_forceshop,glog_fsbns_gamesvronline,glog_fsbns_gamesvrstate,glog_fsbns_itemflow,glog_fsbns_playerexpflow,glog_fsbns_playerlogin,glog_fsbns_playerlogout,glog_fsbns_playerregister,glog_fsbns_pvebattle,glog_fsbns_robot,glog_wooolh_playerlevelflow,glog_wooolh_playerlogout,glog_wooolh_playerlogin,glog_wooolh_playernewchar,mobile_app_dc_click,glog_wooolh_gamesvrstate,glog_ahxt_game_svr_state,glog_ahxt_online,glog_ahxt_pvp,glog_ahxt_prob,glog_ahxt_inst,glog_ahxt_scene,glog_ahxt_role,glog_ahxt_quest,glog_ahxt_pay,glog_ahxt_mall,glog_ahxt_logout,glog_ahxt_login,glog_ahxt_create,glog_ahxt_currency,glog_ahxt_guide,glog_ahxt_guild,glog_ahxt_item,glog_ahxt_chat,glog_ahxt_account,glog_swy_playerlogin,glog_swy_playerlogout,glog_swy_playflow,glog_swy_itemflow,glog_swy_moneyflow,glog_swy_fatigueflow,glog_swy_taskflow,glog_swy_playerguide,glog_swy_guildflow,glog_swy_rollcard,glog_swy_dup,glog_swy_exp,glog_swy_card_get,glog_swy_cardexp,glog_swy_card_up,glog_swy_skill_up,glog_swy_equip_up,glog_swy_arena_challenge,glog_swy_arena_refresh_enemy,glog_swy_arena_prize,glog_swy_arena_win_point,glog_swy_fashion,glog_swy_rename,glog_swy_mail,glog_swy_onlinepvp,glog_swy_intimacy,glog_swy_stone,glog_swy_soul,glog_swy_explore_in,glog_swy_explore_out,glog_swy_explore_event,glog_swy_mpve_battle,glog_swy_match,glog_swy_friend_info,glog_swy_battle_recovery,glog_swy_guild_inc_exp,glog_swy_guild_skillstudy,glog_swy_mpve_exit_msg,glog_swy_cs_mpve_battle,glog_swy_guild_guard,glog_swy_payflow".split(",", -1);
        String[] hour1 = {"11", "12", "13", "14", "15"};
        String[] hour2 = {"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
        //String[] textName = "daoyu_reportsk_all,daoyu_us_bpe_all".split(",", -1);
        String[] hour3 = {"12", "13", "14"};
        String[] hour4 = {"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14",
                "15", "16", "17", "18", "19", "20", "21", "22", "23"};
        String[] hour5 = {"10", "11", "12", "13", "14"};

        //String fileName[] = "daoyu_reportsk_all,daoyu_us_bpe_all,mobile_app_dc_click,glog_fsbns_city,glog_fsbns_cityreset,glog_fsbns_forceshop,glog_fsbns_gamesvronline,glog_fsbns_gamesvrstate,glog_fsbns_itemflow,glog_fsbns_playerexpflow,glog_fsbns_playerlogin,glog_fsbns_playerlogout,glog_fsbns_playerregister,glog_fsbns_pvebattle,glog_fsbns_robot,glog_ahxt_quest,glog_ahxt_role,glog_ahxt_item,glog_ahxt_guild,glog_ahxt_account,glog_ahxt_scene,glog_ahxt_mall,glog_ahxt_inst_exit,glog_ahxt_equip_refine,glog_ahxt_inst,glog_ahxt_equip_mount_stone,glog_ahxt_equip_reborn,glog_ahxt_pvp,glog_ahxt_game_svr_state,glog_ahxt_prob,glog_ahxt_equip_upgrade,glog_ahxt_equip_awake,glog_ahxt_guide,glog_ahxt_online,glog_ahxt_equip_exchange,glog_ahxt_equip_decompose,glog_ahxt_equip_magic,glog_ahxt_avatar_ascend_level,glog_ahxt_skill_level_up,glog_ahxt_equip_inherit,glog_ahxt_equip_sanding,glog_ahxt_avatar_ascend_med_skill,glog_ahxt_avatar_destroy,glog_ahxt_skill_talent,glog_ahxt_avatar_ascend_med_star,glog_ahxt_pet_extend,glog_ahxt_currency,glog_ahxt_rune,glog_ahxt_pet_active,glog_ahxt_truncheon_refine,glog_ahxt_element_upgrade_master_skill,glog_ahxt_pet_unlock,glog_ahxt_truncheon_reborn,glog_ahxt_pvp_team_ladder,glog_ahxt_pvp_single_ladder,glog_ahxt_pvp_3v3,glog_ahxt_element_upgrade_practice_skill,glog_ahxt_runner,glog_ahxt_pvp_5v5,glog_ahxt_attend_prize,glog_ahxt_price_board,glog_ahxt_daily_goals,glog_ahxt_monthly_signin_award,glog_ahxt_first_recharge_award,glog_ahxt_pvp_ares,glog_ahxt_login_activity_buy_half_price_item,glog_ahxt_login_activity,glog_ahxt_guild_profession,glog_ahxt_avatar_ascend_star,glog_ahxt_guild_welfare,glog_ahxt_get_growth_fund,glog_ahxt_guild_prof_task,glog_ahxt_celebration_invest,glog_ahxt_guild_pve,glog_ahxt_pvp_team_ladder_battle,glog_ahxt_buy_growth_fund,glog_ahxt_chat,glog_ahxt_create,glog_ahxt_guild_prof_exp,glog_ahxt_guild_daily_pvp,glog_ahxt_pay,glog_ahxt_logout,glog_ahxt_login,wooolh_character_login_glog,wooolh_character_logout_glog,wooolh_mall_glog,wooolh_item_glog,wooolh_olnum_glog,wooolh_money_consume_glog,wooolh_character_levelup_glog".split(",", -1);
        //String fileName[] = "mobile_app_dc_click,glog_fsbns_city,glog_fsbns_cityreset,glog_fsbns_forceshop,glog_fsbns_gamesvronline,glog_fsbns_gamesvrstate,glog_fsbns_itemflow,glog_fsbns_playerexpflow,glog_fsbns_playerlogin,glog_fsbns_playerlogout,glog_fsbns_playerregister,glog_fsbns_pvebattle,glog_fsbns_robot,glog_ahxt_quest,glog_ahxt_role,glog_ahxt_item,glog_ahxt_guild,glog_ahxt_account,glog_ahxt_scene,glog_ahxt_mall,glog_ahxt_inst_exit,glog_ahxt_equip_refine,glog_ahxt_inst,glog_ahxt_equip_mount_stone,glog_ahxt_equip_reborn,glog_ahxt_pvp,glog_ahxt_game_svr_state,glog_ahxt_prob,glog_ahxt_equip_upgrade,glog_ahxt_equip_awake,glog_ahxt_guide,glog_ahxt_online,glog_ahxt_equip_exchange,glog_ahxt_equip_decompose,glog_ahxt_equip_magic,glog_ahxt_avatar_ascend_level,glog_ahxt_skill_level_up,glog_ahxt_equip_inherit,glog_ahxt_equip_sanding,glog_ahxt_avatar_ascend_med_skill,glog_ahxt_avatar_destroy,glog_ahxt_skill_talent,glog_ahxt_avatar_ascend_med_star,glog_ahxt_pet_extend,glog_ahxt_currency,glog_ahxt_rune,glog_ahxt_pet_active,glog_ahxt_truncheon_refine,glog_ahxt_element_upgrade_master_skill,glog_ahxt_pet_unlock,glog_ahxt_truncheon_reborn,glog_ahxt_pvp_team_ladder,glog_ahxt_pvp_single_ladder,glog_ahxt_pvp_3v3,glog_ahxt_element_upgrade_practice_skill,glog_ahxt_runner,glog_ahxt_pvp_5v5,glog_ahxt_attend_prize,glog_ahxt_price_board,glog_ahxt_daily_goals,glog_ahxt_monthly_signin_award,glog_ahxt_first_recharge_award,glog_ahxt_pvp_ares,glog_ahxt_login_activity_buy_half_price_item,glog_ahxt_login_activity,glog_ahxt_guild_profession,glog_ahxt_avatar_ascend_star,glog_ahxt_guild_welfare,glog_ahxt_get_growth_fund,glog_ahxt_guild_prof_task,glog_ahxt_celebration_invest,glog_ahxt_guild_pve,glog_ahxt_pvp_team_ladder_battle,glog_ahxt_buy_growth_fund,glog_ahxt_chat,glog_ahxt_create,glog_ahxt_guild_prof_exp,glog_ahxt_guild_daily_pvp,glog_ahxt_pay,glog_ahxt_logout,glog_ahxt_login,wooolh_character_login_glog,wooolh_character_logout_glog,wooolh_mall_glog,wooolh_item_glog,wooolh_olnum_glog,wooolh_money_consume_glog,wooolh_character_levelup_glog,wooolh_deposit_glog,daoyu_reportsk_all,daoyu_us_bpe_all".split(",", -1);
        //String fileName[] = "fsol_task_glog,fsol_herostrength_glog,fsol_herostar_glog,fsol_heroequip_glog,fsol_heroperk_glog,fsol_herolevelup_glog,fsol_forceshop_glog,fsol_robot_glog,fsol_cityreset_glog,fsol_city_glog,fsol_character_levelup_glog,fsol_item_glog,fsol_instance_glog,fsol_gamesvrstate_glog,fsol_gamesvronline_glog,fsol_databaseerror_glog,fsol_servercrash_glog,fsol_reg_glog,fsol_character_login_glog,fsol_character_logout_glog".split(",", -1);
        //String fileName[] = "mobile_app_dc_click,wooolh_olnum_glog,wooolh_character_levelup_glog,wooolh_character_logout_glog,wooolh_money_consume_glog,wooolh_character_login_glog,wooolh_mall_glog,wooolh_item_glog,mds_activity_log,mds_client_data,mds_error_log,mds_event_data,mds_user_data,wooolh_deposit_glog,mir2ys_character_logout_glog,mir2ys_character_login_glog,mir2ys_reg_glog,mir2ys_olnum_glog,swy_task_glog,swy_soul_glog,swy_mpve_battle_glog,swy_friend_info_glog,swy_card_up_glog,swy_money_glog,swy_equip_up_glog,swy_explore_event_glog,swy_fashion_glog,swy_explore_in_glog,swy_guild_glog,swy_exp_glog,swy_guild_inc_exp_glog,swy_instance_glog,swy_rollcard_glog,swy_arena_refresh_enemy_glog,swy_guild_guard_glog,swy_cardexp_glog,swy_arena_win_point_glog,swy_intimacy_glog,swy_guild_skillstudy_glog,swy_item_glog,swy_fatigueflow_glog,swy_arena_prize_glog,swy_onlinepvp_glog,swy_cs_match_glog,swy_mpve_exit_msg_glog,swy_newbee_guide_glog,swy_match_glog,swy_friend_gift_info_glog,swy_explore_out_glog,swy_gmgd_glog,swy_arena_challenge_glog,swy_rename_glog,swy_deposit_glog,swy_stone_glog,swy_battle_recovery_glog,swy_card_get_glog,swy_mail_glog,swy_cs_mpve_battle_glog,swy_skill_up_glog,pt_msc_all,swy_character_logout_glog,swy_character_login_glog,swy_mall_glog,swy_push_glog,pt_lsc_all,pt_ssc_all,test_swy_item_glog,test_swy_character_logout_glog,test_swy_character_login_glog,test_swy_task_glog,wooolh_character_glog,swy_guild_exchange_glog,swy_guild_cave_glog,swy_olnum_glog,pt_imagebpe_all,pt_wsc_all,ahxt_equip_awake_glog,ahxt_rune_glog,ahxt_equip_inherit_glog,ahxt_money_glog,ahxt_item_glog,ahxt_celebration_invest_glog,ahxt_equip_exchange_glog,ahxt_buy_growth_fund_glog,ahxt_pvp_glog,ahxt_pvp_3v3_glog,ahxt_character_glog,ahxt_guild_prof_exp_glog,ahxt_character_login_glog,ahxt_game_svr_state_glog,ahxt_avatar_ascend_level_glog,ahxt_pvp_team_ladder_glog,ahxt_attend_prize_glog,ahxt_login_activity_glog,ahxt_skill_talent_glog,ahxt_equip_refine_glog,ahxt_avatar_ascend_med_skill_glog,ahxt_get_growth_fund_glog,ahxt_deposit_glog,ahxt_guild_pve_glog,ahxt_scene_glog,ahxt_avatar_ascend_star_glog,ahxt_account_glog,ahxt_instance_exit_glog,ahxt_task_glog,ahxt_equip_sanding_glog,ahxt_pet_active_glog,ahxt_equip_decompose_glog,ahxt_equip_reborn_glog,ahxt_guild_glog,ahxt_skill_level_up_glog,ahxt_pet_extend_glog,ahxt_login_activity_buy_half_price_item_glog,ahxt_element_upgrade_practice_skill_glog,ahxt_price_board_glog,ahxt_equip_upgrade_glog,ahxt_guild_prof_task_glog,ahxt_prob_glog,ahxt_equip_mount_stone_glog,ahxt_pvp_team_ladder_battle_glog,ahxt_mall_glog,ahxt_runner_glog,ahxt_character_levelup_glog,ahxt_avatar_ascend_med_star_glog,ahxt_guild_daily_pvp_glog,ahxt_chat_glog,ahxt_avatar_destroy_glog,ahxt_monthly_signin_award_glog,ahxt_truncheon_reborn_glog,ahxt_pvp_5v5_glog,ahxt_character_logout_glog,ahxt_daily_goals_glog,ahxt_first_recharge_award_glog,ahxt_pet_unlock_glog,ahxt_olnum_glog,ahxt_guild_profession_glog,ahxt_guild_welfare_glog,ahxt_element_upgrade_master_skill_glog,ahxt_pvp_ares_glog,ahxt_equip_magic_glog,ahxt_truncheon_refine_glog,ahxt_newbee_guide_glog,ahxt_pvp_single_ladder_glog,ahxt_instance_glog,fsol_forceshop_glog,fsol_heroperk_glog,fsol_battlepower_glog,fsol_herolevelup_glog,fsol_servercrash_glog,fsol_item_glog,fsol_instance_glog,fsol_resource_glog,fsol_robot_glog,fsol_databaseerror_glog,fsol_cap_glog,fsol_character_logout_glog,fsol_task_glog,fsol_reg_glog,fsol_gamesvronline_glog,fsol_gamesvrstate_glog,fsol_herostrength_glog,fsol_character_login_glog,fsol_character_levelup_glog,fsol_herocure_glog,fsol_security_fluence_glog,fsol_heroequip_glog,fsol_city_glog,fsol_herostar_glog,fsol_medicalroom_glog,sxcq_money_glog,sxcq_deposit_glog,sxcq_olnum_glog,sxcq_character_logout_glog,sxcq_character_login_glog,sxcq_character_glog,sxcq_guild_member_glog,sxcq_item_glog,sxcq_ce_glog,sxcq_character_levelup_glog,sxcq_instance_glog,sxcq_reg_glog,sxcq_newbee_guide_glog,sxcq_mall_glog,sxcq_task_glog,lzzj_guildcoin_glog,lzzj_arenacoin_glog,lzzj_skillpoint_glog,lzzj_character_glog,lzzj_arena_solo_glog,lzzj_singlescene_glog,lzzj_nqcx_glog,lzzj_cardskill_glog,lzzj_rewardtask_glog,lzzj_homelandfast_glog,lzzj_activity_glog,lzzj_item_glog,lzzj_guildactive_glog,lzzj_gacha_glog,lzzj_sceneopen_glog,lzzj_honorcoin_glog,lzzj_homelandincome_glog,lzzj_worldboss_glog,lzzj_instance_glog,lzzj_trialcoin_glog,lzzj_questionnaire_glog,lzzj_dongtai_shuxing_jiaoyan_glog,lzzj_goldingot_glog,lzzj_guildbattle_skill_glog,lzzj_olnum_glog,lzzj_astraldust_glog,lzzj_arena_team_glog,lzzj_homelandbuild_glog,lzzj_controlswitch_glog,lzzj_character_logout_glog,lzzj_guild_member_glog,lzzj_jinbi_glog,lzzj_expproduce_glog,lzzj_money_glog,lzzj_rolejade_glog,lzzj_dps_jiance_type_glog,lzzj_character_levelup_glog,lzzj_shenqi_jinjie_glog,lzzj_gmcommond_glog,lzzj_guildbattle_card_glog,lzzj_roleequip_glog,lzzj_hometask_glog,lzzj_dongtai_shuxing_jiaoyan_shuzhi_glog,lzzj_roleequip_qianghua_glog,lzzj_shenqi_shengji_glog,lzzj_roleinspire_glog,lzzj_guildparty_glog,lzzj_medalexp_glog,lzzj_homelandcancel_glog,lzzj_teamcopy_glog,lzzj_deposit_glog,lzzj_introtask_glog,lzzj_cheat_glog,lzzj_activation_code_glog,lzzj_roleequip_tupo_glog,lzzj_chenghao_produce_glog,lzzj_dailytask_glog,lzzj_energy_glog,lzzj_guildbattle_candidates_glog,lzzj_pvpresult_glog,lzzj_zuida_shanghai_jiance_glog,lzzj_guildbattle_win_glog,lzzj_roleequip_lvup_glog,lzzj_changename_glog,lzzj_guild_glog,lzzj_guild_instance_glog,lzzj_mall_glog,lzzj_shenqi_produce_glog,lzzj_trial_tower_glog,lzzj_soulstone_glog,lzzj_medalfragment_glog,lzzj_homelandstatus_glog,lzzj_card_glog,lzzj_chenghao_star_glog,lzzj_playpoint_glog,lzzj_character_login_glog,lzzj_roleequip_jinjie_glog,lzzj_newbee_guide_glog,fsol_character_create_glog,fsol_olnum_glog".split(",", -1);

        String fileName[] = "lzzj_deposit_glog,lzzj_character_logout_glog,lzzj_character_login_glog,lzzj_character_glog".split(",", -1);

        String head = "/user/hive/warehouse/dw.db/";
        String head1 = "/tmp/part_date=2017-08-23/";

        String head3 = "/part_date=2017-08-23/";

        System.out.println(fileName.length);
        for (int i = 0; i < fileName.length; i++) {
            for (String hour : hour4) {
                String textFileName = head + fileName[i] + head1 + fileName[i] + "_" + hour + ".txt";
                String seqFileName = head + fileName[i] + head3 + fileName[i] + "_" + hour;

                LOG.info("===================================");
                LOG.info("text files path=" + textFileName);
                LOG.info("text files path=" + seqFileName);
                HDFSRunner.txtFile2SequenceFile(textFileName, seqFileName);
                //LOG.info("sequence files path=" + sequenceFiles[i]);
            }
        }
    }

   /* public static void main(String[] args) {
        try {
            *//*String url;
            URL url2 = new URL("hdfs://sdg/wxm");
            url = "hdfs://" + url2.getHost() + ":" + url2.getPort();
            System.out.println(url);*//*

            System.out.println(FileSystemUtil.getInstance().getUri());
            System.out.println(FileSystemUtil.getInstance().getUri().getHost());
            System.out.println(FileSystemUtil.getInstance().getUri().getPort());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

  /*  public static void main(String[] args) {
        //String textFileName = "/wxm/sxcq_reg_glog/part_date=2017-07-21/2017-07-21.txt";
        //String seqFileName = "/wxm/sxcq_reg_glog/part_date=2017-07-21/sxcq_reg_glog_07_21";
        String inputPath = args[0];
        String outputPath = args[1];

        //HDFSRunner.txtFile2SequenceFile(textFileName, seqFileName);
        try {
            List<String> fileList = HDFSRunner.getFileList(inputPath);
            int index = 0;
            for (String filePath : fileList) {
                index++;
                String output = outputPath + "_" + index;
                LOG.info("===============");
                LOG.info("[input=" + filePath + "  output=" + output + "]");
                HDFSRunner.sequenceFileWrite(filePath, output);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/


   /* public static void main(String[] args) {
        String[] days = {"09", "10"};
        //wooolh_character_logout_glog * area_id=1 group_id=2 account=7 channel=8
        //wooolh_character_glog * area_id=1 group_id=2 account=7 channel=8
        //wooolh_character_levelup_glog * area_id=1 group_id=2 account=7 channel=8
        //wooolh_money_consume_glog * area_id=1 group_id=2 account=7 channel=8
        //wooolh_item_glog * area_id=1 group_id=2 account=7 channel=8
        //wooolh_mall_glog * area_id=1 group_id=2 account=7 channel=8
        //wooolh_deposit_glog * area_id=1 group_id=2 account=7 channel=8
        //wooolh_character_login_glog * area_id=1 group_id=2 account=7 channel=8
        //wooolh_olnum_glog * area_id=1 group_id=2  channel=4

        String[] topics = "wooolh_olnum_glog,wooolh_character_login_glog,wooolh_deposit_glog,wooolh_mall_glog,wooolh_item_glog,wooolh_money_consume_glog,wooolh_character_levelup_glog,wooolh_character_glog,wooolh_character_logout_glog".split(",", -1);

        //System.out.println(topics.length);

        //String[] topics = "wooolh_olnum_glog".split(",", -1);

        for (String topic : topics) {
            for (String day : days) {
                String filePath = "/user/hive/warehouse/dw.db/" + topic + "/part_date=2017-08-" + day + "/part-r-00000";
                String finalPath = "/wxm/" + topic + "/" + topic + "_" + day;

                String mv = "hadoop fs -mv " + filePath + " " + "/wxm/" + topic + "/part_date=2017-08-" + day + "/";
                String cp = "hadoop fs -cp " + finalPath + " " + "/user/hive/warehouse/dw.db/" + topic + "/part_date=2017-08-" + day + "/";

                //System.out.println(filePath);
                //System.out.println(finalPath);

                System.out.println(mv);
                System.out.println(cp);

                //HDFSRunner.sequenceFileReadAndWrite(filePath, finalPath);
            }
        }

        //HDFSRunner.recoverLease("/user/hive/warehouse/dw.db/pt_asc_all/tmp/part_date=2017-08-16/pt_asc_all_09.txt");

        List<String> str = new ArrayList<>();

        for (int i = 0; i < 41; i++) {
            str.add((i + 1) + "");
        }


        String count = "gm01\n" +
                "gm02\n" +
                "gm03\n" +
                "gm04\n" +
                "gm05\n" +
                "gm06\n" +
                "gm07\n" +
                "gm08\n" +
                "gm09\n" +
                "dkm01\n" +
                "dkm02\n" +
                "dkm03\n" +
                "dkm04\n" +
                "dkm05\n" +
                "dkm06\n" +
                "dkm07\n" +
                "dkm08\n" +
                "dkm09\n" +
                "ch01\n" +
                "ch02\n" +
                "ch03\n" +
                "ch04\n" +
                "ch05\n" +
                "ch06\n" +
                "ch07\n" +
                "ch08\n" +
                "ch09\n" +
                "ms01\n" +
                "ms02\n" +
                "ms03\n" +
                "ms04\n" +
                "ms05\n" +
                "ms06\n" +
                "ms07\n" +
                "ms08\n" +
                "ms09\n" +
                "cs01\n" +
                "cs02\n" +
                "cs03\n" +
                "cs04\n" +
                "cs05\n" +
                "cs06\n" +
                "cs07\n" +
                "cs08\n" +
                "cs09\n" +
                "yw01\n" +
                "yw02\n" +
                "yw03\n" +
                "yw04\n" +
                "yw05\n" +
                "yw06\n" +
                "yw07\n" +
                "yw08\n" +
                "yw09\n" +
                "test01\n" +
                "test02\n" +
                "test03\n" +
                "test04\n" +
                "cx01\n" +
                "cx02\n" +
                "cx03\n" +
                "cx04\n" +
                "cx05\n" +
                "cx06\n" +
                "cx07\n" +
                "cx08\n" +
                "cx09";

        String[] counts = count.split("\\n", -1);

        List<String> countList = Arrays.asList(counts);

        //System.out.println(countList);

        //System.out.println(str);

        *//*System.out.println(HDFSRunner.fileReader("/user/hive/warehouse/dw.db/swy_deposit_glog/tmp/part_date=2017-08-07/swy_deposit_glog_20.txt"));

        System.out.println("sleep begin");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("sleep begin");

        System.out.println();*//*

        *//*for (String topic : topics) {
            for (String day : days) {
                String mkdir = "hadoop fs -mkdir /wxm/" + topic + "/part_date=2017-08-" + day;
                System.out.println(mkdir);
            }
        }*//*

       *//* String SHELL_HIVE = "./hive";
        String SHELL_E = "-e";

        String sql = "use dw;insert overwrite directory '/spark/gdata/payment/1_3497128951_2017-08-10_2017-08-11'   SELECT concat_ws('\\t',cast(from_snda_id as string),cast(to_snda_id as string),cast(sum(out_amount) as string)) as col1 FROM dw.pt_c2c_transfer  WHERE PART_DATE >= '2017-08-10' and PART_DATE <= '2017-08-11'  AND from_snda_id <> to_snda_id  GROUP BY from_snda_id,to_snda_id;";

        ProcessBuilderManager processBuilderManager = new ProcessBuilderManager();

        ProcessBuilder pb = new ProcessBuilder(SHELL_HIVE, SHELL_E, "\"" + sql + "\"");
        pb.directory(new File("/app/home/hadoop/src/hive-0.11.0/bin"));

        processBuilderManager.executeResult(pb);*//*
    }*/
}
