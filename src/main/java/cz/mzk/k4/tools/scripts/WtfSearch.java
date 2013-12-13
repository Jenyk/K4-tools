package cz.mzk.k4.tools.scripts;

import cz.mzk.k4.tools.utils.AccessProvider;
import cz.mzk.k4.tools.utils.KrameriusUtils;
import cz.mzk.k4.tools.utils.Script;
import cz.mzk.k4.tools.utils.domain.DigitalObjectModel;
import cz.mzk.k4.tools.utils.fedora.FedoraUtils;
import cz.mzk.k4.tools.workers.RelationshipCounterWorker;
import cz.mzk.k4.tools.workers.UuidWorker;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by holmanj on 12/12/13.
 */
public class WtfSearch implements Script {
    private static final Logger LOGGER = Logger.getLogger(FindLonelyMonographs.class);
    private static UuidWorker worker = new RelationshipCounterWorker(false);
    private AccessProvider accessProvider;
    private KrameriusUtils krameriusUtils;
    private FedoraUtils fedoraUtils;

    /**
     * Spustí RelationshipCounterWorker nad všemi monografiemi
     *
     * @param args - nebere argument
     */
    @Override
    public void run(List<String> args) {
        accessProvider = new AccessProvider();
        krameriusUtils = new KrameriusUtils(accessProvider);
        fedoraUtils = new FedoraUtils(accessProvider);

        // SOLR - porovnat s tripletama (chybějící vazby)
        List<DigitalObjectModel> modely = new ArrayList<DigitalObjectModel>();
        modely.add(DigitalObjectModel.MONOGRAPH);
        modely.add(DigitalObjectModel.PERIODICAL);
//        modely.add(DigitalObjectModel.PAGE);
//        modely.add(DigitalObjectModel.ARCHIVE);
//        modely.add(DigitalObjectModel.ARTICLE);
//        modely.add(DigitalObjectModel.GRAPHIC);
//        modely.add(DigitalObjectModel.INTERNALPART);
//        modely.add(DigitalObjectModel.MANUSCRIPT);
//        modely.add(DigitalObjectModel.MAP);
//        modely.add(DigitalObjectModel.MONOGRAPHUNIT);
//        modely.add(DigitalObjectModel.PERIODICALITEM);
//        modely.add(DigitalObjectModel.PERIODICALVOLUME);
//        modely.add(DigitalObjectModel.SOUND_UNIT);
//        modely.add(DigitalObjectModel.SOUNDRECORDING);
//        modely.add(DigitalObjectModel.SUPPLEMENT);
//        modely.add(DigitalObjectModel.TRACK);

        for (DigitalObjectModel model : modely) {
            List<String> uuidList = krameriusUtils.getUuidsByModelSolr(model.getValue());
            // všechna uuid ve fedoře? =)

            LOGGER.info("Running " + this.getClass() + " on " + accessProvider.getLibraryPrefix() + ", model: " + model.getValue());      // new thread?
            for (String uuid : uuidList) {

                // porovnání s triplety
                // není rekurzivní (každý model zvlášť)
                worker.run(uuid);

                // kontrola závislostí ve fedoře (prohledání stromu)
                fedoraUtils.checkChildrenExistance(uuid);
            }
        }
    }

    @Override
    public String getUsage() {
        return "Projde solr, triplety foxml stromy ve fedoře a vypíše haluze.\n Bez argumentů.";
    }
}