package de.d4k.tinkerforge.stromui.main.ledampel;

import com.tinkerforge.*;
import de.d4k.tinkerforge.stromui.main.Ampel;
import javafx.fxml.Initializable;
import org.devoxx4kids.Bricklet;
import org.devoxx4kids.BrickletReader;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Alexander Bischof
 */
public class LEDampelPresenter implements Initializable {

    @Override
    public void initialize(final URL url, final ResourceBundle rb) {

        try {
            connectBricklet();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void connectBricklet() throws Exception {

        //Get bricklet ids
        BrickletReader readBricklets = new BrickletReader().readBricklets();
        Bricklet currentVoltageBricklet = readBricklets.getBrickletByDeviceId(BrickletVoltageCurrent.DEVICE_IDENTIFIER);
        Bricklet quadName = readBricklets.getBrickletByDeviceId(BrickletIndustrialQuadRelay.DEVICE_IDENTIFIER);

        //Create connection
        IPConnection ipcon = new IPConnection();
        BrickletVoltageCurrent cv = new BrickletVoltageCurrent(currentVoltageBricklet.getUid(), ipcon);
        BrickletIndustrialQuadRelay industrialQuadRelay = new BrickletIndustrialQuadRelay(quadName.getUid(), ipcon);
        ipcon.connect(BrickletReader.HOST, BrickletReader.PORT);

        industrialQuadRelay.setValue(0);

        cv.setVoltageCallbackPeriod(1000l);
        cv.addVoltageListener(voltage -> {
            try {
                Ampel ampel = new Ampel(industrialQuadRelay);
                if (voltage < 800) {
                    ampel.aus();
                } else if (inRange(voltage, 800, 3300)) {
                    ampel.gruen();
                } else if (inRange(voltage, 3300, 4200)) {
                    ampel.gruen().gelb();
                } else if (inRange(voltage, 4200, 5000)) {
                    ampel.gruen().rot().gelb();
                } else {
                    ampel.gelb().rot().gruen().luefter();
                }

            } catch (TimeoutException | NotConnectedException e) {
                e.printStackTrace();
            }
        });
    }

    private boolean inRange(int voltage, int lowerBound, int upperBound) {
        return voltage > lowerBound && voltage <= upperBound;
    }
}