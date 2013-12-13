package cz.mzk.k4.tools.workers;

import cz.mzk.k4.tools.utils.AbbyUtils;
import cz.mzk.k4.tools.utils.AccessProvider;
import cz.mzk.k4.tools.utils.fedora.Constants;
import cz.mzk.k4.tools.utils.fedora.FedoraUtils;
import cz.mzk.k4.tools.utils.FormatConvertor;
import cz.mzk.k4.tools.utils.exception.CreateObjectException;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;


/**
 * @author: Martin Rumanek
 * @version: 11/26/13
 */
public class AddOcrWorker extends UuidWorker {
    private FedoraUtils fedoraUtils = new FedoraUtils(new AccessProvider());
    private AbbyUtils abbyUtils = new AbbyUtils();
    private static final Logger LOGGER = Logger.getLogger(AddOcrWorker.class);

    public AddOcrWorker(boolean writeEnabled) {
        super(writeEnabled);
    }

    @Override
    public void run(String uuid) {
        try {
            String mimetype = fedoraUtils.getMimeTypeForStream(uuid, Constants.DATASTREAM_ID.IMG_FULL.getValue());
            InputStream rawInputStream = fedoraUtils.getImgFull(uuid, mimetype);
            LOGGER.info("Page " + uuid + " is sending to OCR server");
            if (fedoraUtils.getOcr(uuid) == null) {
                InputStream jpgInputStream;
                if ("image/vnd.djvu".equals(mimetype)) {
                    jpgInputStream = FormatConvertor.convertDjvuToJpg(rawInputStream);
                } else {
                    jpgInputStream = rawInputStream;
                }

                byte[] img = org.apache.commons.io.IOUtils.toByteArray(jpgInputStream);
                String[] ocr = abbyUtils.getOcr(img);
                try {
                    fedoraUtils.setOcr(uuid, ocr[0]);
                    fedoraUtils.setAltoOcr(uuid, ocr[1]);
                    LOGGER.info("OCR to " + uuid + " was added");
                } catch (CreateObjectException e) {
                    LOGGER.error("CreateObject exception " + uuid);
                }
            } else {
                LOGGER.info("Page " + uuid + " has already OCR");
            }

        } catch (IOException e) {
            LOGGER.error("IO error " + uuid);
        }
    }
}