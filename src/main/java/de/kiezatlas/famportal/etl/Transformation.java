package de.kiezatlas.famportal.etl;

import java.util.HashMap;
import java.util.Map;



public class Transformation {

    public static Map<String, String> CRITERIA_MAP = new HashMap();
    public static Map<String, String> CATEGORY_MAP = new HashMap();

    static {
        CRITERIA_MAP.put("t-253478",  "ka2.criteria.kategorie.facet");      // Mitte
        CRITERIA_MAP.put("t-239975",  "ka2.criteria.kategorie.facet");      // FrKr
        CRITERIA_MAP.put("t-253482",  "ka2.criteria.angebot.facet");        // Mitte
        CRITERIA_MAP.put("t-239977",  "ka2.criteria.angebot.facet");        // FrKr
        CRITERIA_MAP.put("t-253480",  "ka2.criteria.zielgruppe.facet");     // Mitte
        CRITERIA_MAP.put("t-239979",  "ka2.criteria.zielgruppe.facet");     // FrKr
        CRITERIA_MAP.put("t-1220610", "ka2.criteria.ueberregional.facet");  // FrKr
    }

    static {
        // Kategorien
        CATEGORY_MAP.put("t-253525", "ka2.category.ausbildung_und_arbeit");                                 // Mitte
        CATEGORY_MAP.put("t-240011", "ka2.category.ausbildung_und_arbeit");                                 // FrKr
        CATEGORY_MAP.put("t-253526", "ka2.category.beratung");                                              // Mitte
        CATEGORY_MAP.put("t-240012", "ka2.category.beratung");                                              // FrKr
        CATEGORY_MAP.put("t-253527", "ka2.category.familie");                                               // Mitte
        CATEGORY_MAP.put("t-240013", "ka2.category.familie");                                               // FrKr
        CATEGORY_MAP.put("t-507124", "ka2.category.frauen");                                                // Mitte
        CATEGORY_MAP.put("t-240014", "ka2.category.gesundheit");                                            // FrKr
        CATEGORY_MAP.put("t-253528", "ka2.category.gesundheit_und_behinderung");                            // Mitte
        CATEGORY_MAP.put("t-240016", "ka2.category.jugend");                                                // FrKr
        CATEGORY_MAP.put("t-253530", "ka2.category.jugendamt");                                             // Mitte
        CATEGORY_MAP.put("t-240017", "ka2.category.jugendamt");                                             // FrKr
        CATEGORY_MAP.put("t-253529", "ka2.category.kinder_und_jugendfreizeit");                             // Mitte
        CATEGORY_MAP.put("t-240018", "ka2.category.kinder");                                                // FrKr
        CATEGORY_MAP.put("t-253531", "ka2.category.kinderbetreuung");                                       // Mitte
        CATEGORY_MAP.put("t-240019", "ka2.category.kinderbetreuung");                                       // FrKr
        CATEGORY_MAP.put("t-253532", "ka2.category.kultur_und_bildung");                                    // Mitte
        CATEGORY_MAP.put("t-240020", "ka2.category.kultur_und_bildung");                                    // FrKr
        CATEGORY_MAP.put("t-240015", "ka2.category.menschen_mit_behinderungen");                            // FrKr
        CATEGORY_MAP.put("t-253533", "ka2.category.nachbarschaftstreffpunkte_und_stadtteilarbeit");         // Mitte
        CATEGORY_MAP.put("t-240021", "ka2.category.nachbarschaftstreffpunkte_und_stadtteilarbeit");         // FrKr
        CATEGORY_MAP.put("t-266441", "ka2.category.netzwerk");                                              // Mitte
        CATEGORY_MAP.put("t-253534", "ka2.category.notdienste");                                            // Mitte
        CATEGORY_MAP.put("t-240022", "ka2.category.notdienste");                                            // FrKr
        CATEGORY_MAP.put("t-253535", "ka2.category.schule");                                                // Mitte
        CATEGORY_MAP.put("t-240023", "ka2.category.schule");                                                // FrKr
        CATEGORY_MAP.put("t-266443", "ka2.category.seniorinnen_und_senioren");                              // Mitte
        CATEGORY_MAP.put("t-240026", "ka2.category.seniorinnen_und_senioren");                              // FrKr
        CATEGORY_MAP.put("t-240025", "ka2.category.spezielle_angebote_fuer_migranten_und_migrantinnen");    // FrKr
        CATEGORY_MAP.put("t-266440", "ka2.category.sport");                                                 // Mitte
        CATEGORY_MAP.put("t-240024", "ka2.category.sport");                                                 // FrKr
        CATEGORY_MAP.put("t-266442", "ka2.category.wohnung_und_unterkunft");                                // Mitte

        // Angebote
        CATEGORY_MAP.put("t-253548", "ka2.category.angebote_fuer_menschen_mit_behinderung");                // Mitte
        CATEGORY_MAP.put("t-240059", "ka2.category.angebote_fuer_menschen_mit_behinderung");                // FrKr
        CATEGORY_MAP.put("t-240041", "ka2.category.angebote_in_familienbildung_elternbildung_familienselbsthilfe_familienberatung_familienerholung");   // FrKr
        CATEGORY_MAP.put("t-240083", "ka2.category.babysitter");                                            // FrKr
        CATEGORY_MAP.put("t-240038", "ka2.category.beratung_bei_trennung_und_scheidung");                   // FrKr
        CATEGORY_MAP.put("t-240094", "ka2.category.beratung_und_hilfe_in_krisensituationen");               // FrKr
        CATEGORY_MAP.put("t-253549", "ka2.category.beratungsangebote");                                     // Mitte
        CATEGORY_MAP.put("t-240028", "ka2.category.berufsausbildung");                                      // FrKr
        CATEGORY_MAP.put("t-240036", "ka2.category.berufsberatung_und_orientierung");                       // FrKr
        CATEGORY_MAP.put("t-240065", "ka2.category.berufsberatung_und_orientierung");                       // FrKr
        CATEGORY_MAP.put("t-240034", "ka2.category.drogenberatung");                                        // FrKr
        CATEGORY_MAP.put("t-240058", "ka2.category.drogenentzugseinrichtung");                              // FrKr
        CATEGORY_MAP.put("t-266732", "ka2.category.eltern_kind_gruppen");                                   // FrKr
        CATEGORY_MAP.put("t-240060", "ka2.category.fallmanagement_jugendamt_behindertenhilfe");             // FrKr
        CATEGORY_MAP.put("t-253550", "ka2.category.erziehungs_und_familienberatung");                       // Mitte
        CATEGORY_MAP.put("t-240070", "ka2.category.erziehungs_und_familienberatung");                       // FrKr
        CATEGORY_MAP.put("t-240040", "ka2.category.erziehungs_und_familienberatung");                       // FrKr
        CATEGORY_MAP.put("t-240043", "ka2.category.familiencafes");                                         // FrKr
        CATEGORY_MAP.put("t-240049", "ka2.category.familienkasse");                                         // FrKr
        CATEGORY_MAP.put("t-240042", "ka2.category.familienzentren");                                       // FrKr
        CATEGORY_MAP.put("t-240030", "ka2.category.fort_und_weiterbildungen");                              // FrKr
        CATEGORY_MAP.put("t-240050", "ka2.category.frauenhaeuser");                                         // FrKr
        CATEGORY_MAP.put("t-253551", "ka2.category.frauenprojekte");                                        // Mitte
        CATEGORY_MAP.put("t-240056", "ka2.category.geburtshaeuser");                                        // FrKr
        CATEGORY_MAP.put("t-240051", "ka2.category.gezielte_gesundheitsfoerderung");                        // FrKr
        CATEGORY_MAP.put("t-240082", "ka2.category.grosselterndienst");                                     // FrKr
        CATEGORY_MAP.put("t-240097", "ka2.category.grund_und_oberschulen");                                 // FrKr
        CATEGORY_MAP.put("t-253552", "ka2.category.grundschulen");                                          // Mitte
        CATEGORY_MAP.put("t-266731", "ka2.category.hausaufgabenbetreuung");                                 // FrKr
        CATEGORY_MAP.put("t-240055", "ka2.category.hebammen");                                              // FrKr
        CATEGORY_MAP.put("t-240047", "ka2.category.hilfen_in_besonderen_notsituationen");                   // FrKr
        CATEGORY_MAP.put("t-253553", "ka2.category.hilfen_zur_erziehung");                                  // Mitte
        CATEGORY_MAP.put("t-240073", "ka2.category.hilfen_zur_erziehung");                                  // FrKr
        CATEGORY_MAP.put("t-240057", "ka2.category.hiv_beratung");                                          // FrKr
        CATEGORY_MAP.put("t-240096", "ka2.category.hotline");                                               // FrKr
        CATEGORY_MAP.put("t-240031", "ka2.category.jobcenter_agentur_fuer_arbeit");                         // FrKr
        CATEGORY_MAP.put("t-253554", "ka2.category.jugendberatung_und_jugendberufshilfe");                  // Mitte
        CATEGORY_MAP.put("t-240062", "ka2.category.jugendfreizeiteinrichtungen");                           // FrKr
        CATEGORY_MAP.put("t-240074", "ka2.category.jugendgerichtshilfe_bewaehrungshilfe");                  // FrKr
        CATEGORY_MAP.put("t-240067", "ka2.category.jugendgerichtshilfe_bewaehrungshilfe");                  // FrKr
        CATEGORY_MAP.put("t-240066", "ka2.category.jugendnotdienst");                                       // FrKr
        CATEGORY_MAP.put("t-240063", "ka2.category.jugendsozialarbeit");                                    // FrKr
        CATEGORY_MAP.put("t-240087", "ka2.category.kieznahe_mehrgenerationsangebote");                      // FrKr
        CATEGORY_MAP.put("t-240054", "ka2.category.kinder_und_jugendgesundheitsdienste");                   // FrKr
        CATEGORY_MAP.put("t-240095", "ka2.category.kinder_und_jugendnotdienst");                            // FrKr
        CATEGORY_MAP.put("t-240039", "ka2.category.kinder_und_jugendspychiatrische_beratungsstelle");       // FrKr
        CATEGORY_MAP.put("t-240075", "ka2.category.kinderfreizeiteinrichtungen");                           // FrKr
        CATEGORY_MAP.put("t-240077", "ka2.category.kindernotdienst");                                       // FrKr
        CATEGORY_MAP.put("t-240076", "ka2.category.kinderschutzteam");                                      // FrKr
        CATEGORY_MAP.put("t-240045", "ka2.category.kinderspielplaetze");                                    // FrKr
        CATEGORY_MAP.put("t-240085", "ka2.category.kirchen_und_religionsgemeinschaften");                   // FrKr
        CATEGORY_MAP.put("t-240068", "ka2.category.kjpd");                                                  // FrKr
        CATEGORY_MAP.put("t-240052", "ka2.category.krankenhaeuser");                                        // FrKr
        CATEGORY_MAP.put("t-240084", "ka2.category.kultur_und_bildungseinrichtungen");                      // FrKr
        CATEGORY_MAP.put("t-240069", "ka2.category.leitung_und_team_des_jugendamtes");                      // FrKr
        CATEGORY_MAP.put("t-240079", "ka2.category.logopaedische_praxen");                                  // FrKr
        CATEGORY_MAP.put("t-240037", "ka2.category.mietberatung");                                          // FrKr
        CATEGORY_MAP.put("t-253555", "ka2.category.migration_und_integration");                             // Mitte
        CATEGORY_MAP.put("t-240086", "ka2.category.musikschulen_vhs");                                      // FrKr
        CATEGORY_MAP.put("t-253556", "ka2.category.oberschulen");                                           // Mitte
        CATEGORY_MAP.put("t-240088", "ka2.category.offene_treffpunkte_mit_sozialkulturellem_charakter");    // FrKr
        CATEGORY_MAP.put("t-240029", "ka2.category.qualifizierung_und_beschaeftigung");                     // FrKr
        CATEGORY_MAP.put("t-240090", "ka2.category.quartiersmanagement");                                   // FrKr
        CATEGORY_MAP.put("t-240035", "ka2.category.rechtsberatung");                                        // FrKr
        CATEGORY_MAP.put("t-240072", "ka2.category.regionale_dienste");                                     // FrKr
        CATEGORY_MAP.put("t-240061", "ka2.category.reha_im_jobcenter_arbeitsamt");                          // FrKr
        CATEGORY_MAP.put("t-240098", "ka2.category.schulbezogene_angebote_der_jugendhilfe");                // FrKr
        CATEGORY_MAP.put("t-253557", "ka2.category.schuldner_und_insolvenzberatung");                       // Mitte
        CATEGORY_MAP.put("t-240032", "ka2.category.schuldner_und_insolvenzberatung");                       // FrKr
        CATEGORY_MAP.put("t-253558", "ka2.category.schulpsychologische_beratungsstelle");                   // Mitte
        CATEGORY_MAP.put("t-255713", "ka2.category.schulsporthalle");                                       // FrKr
        CATEGORY_MAP.put("t-255714", "ka2.category.schwimmbad");                                            // FrKr
        CATEGORY_MAP.put("t-240093", "ka2.category.selbsthilfegruppen");                                    // FrKr
        CATEGORY_MAP.put("t-253559", "ka2.category.sonstige_schulen");                                      // Mitte
        CATEGORY_MAP.put("t-240033", "ka2.category.sozialhilfeberatung");                                   // FrKr
        CATEGORY_MAP.put("t-240099", "ka2.category.sport_und_bolzplaetze");                                 // FrKr
        CATEGORY_MAP.put("t-240101", "ka2.category.sportbezogene_jugendfreizeitangebote");                  // FrKr
        CATEGORY_MAP.put("t-240100", "ka2.category.sportvereine");                                          // FrKr
        CATEGORY_MAP.put("t-240089", "ka2.category.stadtteilarbeit");                                       // FrKr
        CATEGORY_MAP.put("t-240092", "ka2.category.streetworker");                                          // FrKr
        CATEGORY_MAP.put("t-240080", "ka2.category.tagesbetreuung_fuer_kinder");                            // FrKr
        CATEGORY_MAP.put("t-240081", "ka2.category.tagesbetreuung_fuer_kinder");                            // FrKr
        CATEGORY_MAP.put("t-240044", "ka2.category.traeger_der_jugendhilfe");                               // FrKr
        CATEGORY_MAP.put("t-240048", "ka2.category.unterhalt_vaterschaftsanerkennung_im_jugendamt");        // FrKr
        CATEGORY_MAP.put("t-262210", "ka2.category.wohnen");                                                // FrKr
        CATEGORY_MAP.put("t-240053", "ka2.category.aerzte");                                                // FrKr

        // Zielgruppen
        CATEGORY_MAP.put("t-253538", "ka2.category.alle_altersgruppen");                                        // Mitte
        CATEGORY_MAP.put("t-240105", "ka2.category.alle_altersgruppen");                                        // FrKr
        CATEGORY_MAP.put("t-240106", "ka2.category.auszubildende");                                             // FrKr
        CATEGORY_MAP.put("t-253539", "ka2.category.auszubildende_und_junge_erwachsene");                        // Mitte
        CATEGORY_MAP.put("t-253540", "ka2.category.erwachsene");                                                // Mitte
        CATEGORY_MAP.put("t-240108", "ka2.category.erwachsene");                                                // FrKr
        CATEGORY_MAP.put("t-240109", "ka2.category.familien");                                                  // FrKr
        CATEGORY_MAP.put("t-253541", "ka2.category.familien_und_alleinerziehende_mit_kindern_im_schulalter");   // Mitte
        CATEGORY_MAP.put("t-240111", "ka2.category.familien_und_alleinerziehende_mit_kindern_im_schulalter");   // FrKr
        CATEGORY_MAP.put("t-253542", "ka2.category.familien_und_alleinerziehende_mit_kleinkindern");            // Mitte
        CATEGORY_MAP.put("t-240112", "ka2.category.familien_und_alleinerziehende_mit_kleinkindern");            // FrKr
        CATEGORY_MAP.put("t-253543", "ka2.category.familien_und_alleinerziehende_mit_saeuglingen");             // Mitte
        CATEGORY_MAP.put("t-240113", "ka2.category.familien_und_alleinerziehende_mit_saeuglingen");             // FrKr
        CATEGORY_MAP.put("t-253544", "ka2.category.zielgruppe_frauen");                                         // Mitte
        CATEGORY_MAP.put("t-240114", "ka2.category.zielgruppe_frauen");                                         // FrKr
        CATEGORY_MAP.put("t-253545", "ka2.category.jugendliche");                                               // Mitte
        CATEGORY_MAP.put("t-240115", "ka2.category.jugendliche");                                               // FrKr
        CATEGORY_MAP.put("t-240107", "ka2.category.junge_erwachsene");                                          // FrKr
        CATEGORY_MAP.put("t-240110", "ka2.category.zielgruppe_kinder");                                         // FrKr
        CATEGORY_MAP.put("t-253546", "ka2.category.kinder_im_schulalter");                                      // Mitte
        CATEGORY_MAP.put("t-240116", "ka2.category.kinder_im_schulalter");                                      // FrKr
        CATEGORY_MAP.put("t-240117", "ka2.category.zielgruppe_seniorinnen_und_senioren");                       // FrKr
        CATEGORY_MAP.put("t-253547", "ka2.category.sonstige");                                                  // Mitte
        CATEGORY_MAP.put("t-240118", "ka2.category.sonstige");                                                  // FrKr

        // Überregional
        CATEGORY_MAP.put("t-1220613", "ka2.category.berlinweit");                                               // FrKr
        CATEGORY_MAP.put("t-1220614", "ka2.category.gesamtbezirk");                                             // FrKr
    }
}
