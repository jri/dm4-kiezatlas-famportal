package de.kiezatlas.famportal.etl;

import java.util.HashMap;
import java.util.Map;



public class Transformation {

    public static Map<String, String> CRITERIA_MAP = new HashMap();
    public static Map<String, String> CATEGORY_MAP = new HashMap();

    static {
        CRITERIA_MAP.put("t-253478", "ka2.criteria.kategorie.facet");
        CRITERIA_MAP.put("t-253482", "ka2.criteria.angebot.facet");
        CRITERIA_MAP.put("t-253480", "ka2.criteria.zielgruppe.facet");
    }

    static {
        // Kategorien
        CATEGORY_MAP.put("t-253525", "ka2.category.ausbildung_und_arbeit");
        CATEGORY_MAP.put("t-253526", "ka2.category.beratung");
        CATEGORY_MAP.put("t-253527", "ka2.category.familie");
        CATEGORY_MAP.put("t-507124", "ka2.category.frauen");
        CATEGORY_MAP.put("t-253528", "ka2.category.gesundheit_und_behinderung");
        CATEGORY_MAP.put("t-253530", "ka2.category.jugendamt");
        CATEGORY_MAP.put("t-253529", "ka2.category.kinder_und_jugendfreizeit");
        CATEGORY_MAP.put("t-253531", "ka2.category.kinderbetreuung");
        CATEGORY_MAP.put("t-253532", "ka2.category.kultur_und_bildung");
        CATEGORY_MAP.put("t-253533", "ka2.category.nachbarschaftstreffpunkte_und_stadtteilarbeit");
        CATEGORY_MAP.put("t-266441", "ka2.category.netzwerk");
        CATEGORY_MAP.put("t-253534", "ka2.category.not");
        CATEGORY_MAP.put("t-253535", "ka2.category.schule");
        CATEGORY_MAP.put("t-266443", "ka2.category.seniorinnen");
        CATEGORY_MAP.put("t-266440", "ka2.category.sport");
        CATEGORY_MAP.put("t-266442", "ka2.category.wohnung_und_unterkunft");

        // Angebote
        CATEGORY_MAP.put("t-253548", "ka2.category.angebote_fuer_menschen_mit_behinderung");
        CATEGORY_MAP.put("t-253549", "ka2.category.beratungsangebote");
        CATEGORY_MAP.put("t-253550", "ka2.category.erziehungs_und_familienberatung");
        CATEGORY_MAP.put("t-253551", "ka2.category.frauenprojekte");
        CATEGORY_MAP.put("t-253552", "ka2.category.grundschulen");
        CATEGORY_MAP.put("t-253553", "ka2.category.hilfen_zur_erziehung");
        CATEGORY_MAP.put("t-253554", "ka2.category.jugendberatung_und_jugendberufshilfe");
        CATEGORY_MAP.put("t-253555", "ka2.category.migration_und_integration");
        CATEGORY_MAP.put("t-253556", "ka2.category.oberschulen");
        CATEGORY_MAP.put("t-253557", "ka2.category.schuldner_und_insolvenzberatung");
        CATEGORY_MAP.put("t-253558", "ka2.category.schulpsychologische_beratungsstelle");
        CATEGORY_MAP.put("t-253559", "ka2.category.sonstige_schulen");

        // Zielgruppen
        CATEGORY_MAP.put("t-253538", "ka2.category.alle_altersgruppen");
        CATEGORY_MAP.put("t-253539", "ka2.category.auszubildende_und_junge_erwachsene");
        CATEGORY_MAP.put("t-253540", "ka2.category.erwachsene");
        CATEGORY_MAP.put("t-253541", "ka2.category.familien_und_alleinerziehende_mit_kindern_im_schulalter");
        CATEGORY_MAP.put("t-253542", "ka2.category.familien_und_alleinerziehende_mit_kleinkindern");
        CATEGORY_MAP.put("t-253543", "ka2.category.familien_und_alleinerziehende_mit_saeuglingen");
        CATEGORY_MAP.put("t-253544", "ka2.category.zielgruppe_frauen");
        CATEGORY_MAP.put("t-253545", "ka2.category.jugendliche");
        CATEGORY_MAP.put("t-253546", "ka2.category.kinder_im_schulalter");
        CATEGORY_MAP.put("t-253547", "ka2.category.sonstige");
    }
}
