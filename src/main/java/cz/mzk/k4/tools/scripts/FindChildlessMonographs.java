package cz.mzk.k4.tools.scripts;

import cz.mzk.k4.tools.utils.AccessProvider;
import cz.mzk.k4.tools.utils.Script;
import cz.mzk.k4.tools.utils.fedoraUtils.FedoraUtils;
import cz.mzk.k4.tools.utils.fedoraUtils.domain.DigitalObjectModel;
import cz.mzk.k4.tools.workers.UuidWorker;
import cz.mzk.k4.tools.workers.ChildCounterWorker;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Jan Holman
 * Date: 11/8/13
 * Time: 10:08 AM
 */
public class FindChildlessMonographs implements Script {

    private static FedoraUtils fedoraUtils = new FedoraUtils(new AccessProvider());
    private static UuidWorker worker = new ChildCounterWorker(false);

    /**
     * Spustí ChildCounterWorker nad všemi monografiemi
     * @param args - nebere argument
     */
    @Override
    public void run(List<String> args) {
        fedoraUtils.applyToAllUuidOfModel(DigitalObjectModel.MONOGRAPH, worker, 5);
    }

    @Override
    public String getUsage() {
        return "vypisBezdetneMonografie - Vypíše monografie s rozbitými vazbami v ritriplets";
    }
}
