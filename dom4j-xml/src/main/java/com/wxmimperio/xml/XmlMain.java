package com.wxmimperio.xml;

import com.google.gson.*;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.sound.midi.Soundbank;
import java.io.File;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by wxmimperio on 2017/4/19.
 */
public class XmlMain {
    public static void main(String[] args) {
        String fileName = System.getProperty("user.dir") + "/src/main/resources/swy_glog_20170814_fix.xml";
        File inputXml = new File(fileName);
        SAXReader saxReader = new SAXReader();

        StringBuffer stringBuffer = new StringBuffer();

        try {

            Document document = saxReader.read(inputXml);
            Element rootElement = document.getRootElement();

            int index = 0;

            for (Iterator i = rootElement.elementIterator(); i.hasNext(); ) {
                JsonObject jsonObject = new JsonObject();

                Element firstElement = (Element) i.next();
                jsonObject.addProperty("tableName", firstElement.attribute("name").getText());

                /*JsonArray fields = new JsonArray();
                for (Iterator j = firstElement.elementIterator(); j.hasNext(); ) {
                    Element secondElement = (Element) j.next();
                    Map<String, String> field = new ConcurrentHashMap<String, String>();
                    field.put("name", secondElement.attribute("name").getText());
                    field.put("type", secondElement.attribute("type").getText());
                    field.put("desc", secondElement.attribute("desc").getText());
                    fields.add(new JsonParser().parse(new Gson().toJson(field)));
                }
                jsonObject.add("fields", fields.getAsJsonArray());*/

                System.out.println("===============================================");

                System.out.println(jsonObject.get("tableName"));

                if (jsonObject.get("tableName").getAsString().startsWith("swy_")) {
                    index++;
                    stringBuffer.append(jsonObject.get("tableName")).append(",");
                }
                /*System.out.println(jsonObject.get("desc"));

                JsonArray resultFields = jsonObject.get("fields").getAsJsonArray();

                Gson elementGson = new Gson();
                for (JsonElement jsonElement : resultFields) {
                    System.out.println(elementGson.fromJson(jsonElement, Node.class).getName() + " " +
                            elementGson.fromJson(jsonElement, Node.class).getType() + " " +
                            elementGson.fromJson(jsonElement, Node.class).getDesc());
                }*/

            }

            System.out.println(stringBuffer.toString().replaceAll("\"", ""));
            System.out.println(index);
           /* String str = "ahxt_account_glog,ahxt_chat_glog,ahxt_character_glog,ahxt_money_glog,ahxt_newbee_guide_glog,ahxt_guild_glog,ahxt_item_glog,ahxt_character_login_glog,ahxt_character_logout_glog,ahxt_mall_glog,ahxt_task_glog,ahxt_character_levelup_glog,ahxt_scene_glog,ahxt_instance_glog,ahxt_pvp_glog,ahxt_olnum_glog,ahxt_game_svr_state_glog,ahxt_prob_glog,ahxt_equip_refine_glog,ahxt_equip_mount_stone_glog,ahxt_equip_reborn_glog,ahxt_equip_magic_glog,ahxt_equip_inherit_glog,ahxt_equip_upgrade_glog,ahxt_equip_awake_glog,ahxt_equip_sanding_glog,ahxt_equip_decompose_glog,ahxt_equip_exchange_glog,ahxt_skill_level_up_glog,ahxt_skill_talent_glog,ahxt_avatar_ascend_level_glog,ahxt_avatar_ascend_star_glog,ahxt_avatar_ascend_med_star_glog,ahxt_avatar_ascend_med_skill_glog,ahxt_avatar_destroy_glog,ahxt_rune_glog,ahxt_pet_unlock_glog,ahxt_pet_extend_glog,ahxt_pet_active_glog,ahxt_truncheon_refine_glog,ahxt_truncheon_reborn_glog,ahxt_element_upgrade_master_skill_glog,ahxt_element_upgrade_practice_skill_glog,ahxt_pvp_single_ladder_glog,ahxt_runner_glog,ahxt_price_board_glog,ahxt_attend_prize_glog,ahxt_monthly_signin_award_glog,ahxt_daily_goals_glog,ahxt_login_activity_glog,ahxt_login_activity_buy_half_price_item_glog,ahxt_guild_profession_glog,ahxt_guild_prof_task_glog,ahxt_guild_prof_exp_glog,ahxt_first_recharge_award_glog,ahxt_guild_pve_glog,ahxt_pvp_ares_glog,ahxt_guild_daily_pvp_glog,ahxt_celebration_invest_glog,ahxt_deposit_glog,ahxt_pvp_5v5_glog,ahxt_pvp_team_ladder_battle_glog,ahxt_buy_growth_fund_glog,ahxt_get_growth_fund_glog,ahxt_instance_exit_glog,ahxt_pvp_team_ladder_glog,ahxt_pvp_3v3_glog,ahxt_guild_welfare_glog";
            System.out.println(str.split(",", -1).length);*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
