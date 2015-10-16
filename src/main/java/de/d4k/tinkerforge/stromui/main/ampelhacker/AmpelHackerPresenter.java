package de.d4k.tinkerforge.stromui.main.ampelhacker;

import com.tinkerforge.BrickletIndustrialQuadRelay;
import com.tinkerforge.IPConnection;
import de.d4k.tinkerforge.stromui.main.Ampel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import org.devoxx4kids.Bricklet;
import org.devoxx4kids.BrickletReader;

import java.net.URL;
import java.util.ResourceBundle;

import static java.lang.Integer.valueOf;
import static java.util.concurrent.TimeUnit.SECONDS;
import static java.util.stream.Stream.of;

/**
 * @author Alexander Bischof
 */
public class AmpelHackerPresenter implements Initializable {

    private Ampel ampel;

    @FXML
    TextArea textArea;

    @FXML
    protected void buttonPressed() {
        String text = textArea.getText();

        of(text.split("\\r?\\n")).map(c -> c.toLowerCase().trim()).forEach(command -> {
            try {
                switch (command) {
                    case "gruen":
                        ampel.gruen();
                        break;
                    case "rot":
                        ampel.rot();
                        break;
                    case "gelb":
                        ampel.gelb();
                        break;
                    case "rotaus":
                    case "rot aus":
                        ampel.rotAus();
                        break;
                    case "gelbaus":
                    case "gelb aus":
                        ampel.gelbAus();
                        break;
                    case "gruenaus":
                    case "gruen aus":
                        ampel.gruenAus();
                        break;
                    case "luefter":
                        ampel.luefter();
                        break;
                    case "luefteraus":
                    case "luefter aus":
                        ampel.luefterAus();
                        break;
                    case "allesaus":
                        ampel.aus();
                        break;
                    default:
                        if (command.startsWith("warte ")) {
                            try {
                                SECONDS.sleep(valueOf(command.split(" ")[1]));
                            } catch (ArrayIndexOutOfBoundsException | InterruptedException e) {
                                e.printStackTrace();
                            } catch (NumberFormatException e) {
                                System.err.println("Bitte gib als zweiten Wert eine Zahl ein.");
                            }
                        } else {
                            System.err.println("Befehl unbekannt: " + command);
                        }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });


        //textArea.clear();
    }

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
        Bricklet quadName = readBricklets.getBrickletByDeviceId(BrickletIndustrialQuadRelay.DEVICE_IDENTIFIER);

        //Create connection
        IPConnection ipcon = new IPConnection();
        BrickletIndustrialQuadRelay industrialQuadRelay = new BrickletIndustrialQuadRelay(quadName.getUid(), ipcon);
        ipcon.connect(BrickletReader.HOST, BrickletReader.PORT);

        industrialQuadRelay.setValue(0);
        this.ampel = new Ampel(industrialQuadRelay);
    }

    private boolean inRange(int voltage, int lowerBound, int upperBound) {
        return voltage > lowerBound && voltage <= upperBound;
    }
}