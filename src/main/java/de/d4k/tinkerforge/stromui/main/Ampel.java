package de.d4k.tinkerforge.stromui.main;

import com.tinkerforge.BrickletIndustrialQuadRelay;
import com.tinkerforge.NotConnectedException;
import com.tinkerforge.TimeoutException;

public class Ampel {
        private final BrickletIndustrialQuadRelay industrialQuadRelay;

        public Ampel(BrickletIndustrialQuadRelay industrialQuadRelay) {
            this.industrialQuadRelay = industrialQuadRelay;
        }

        public Ampel aus() throws TimeoutException, NotConnectedException {
            industrialQuadRelay.setValue(0);
            return this;
        }

        public Ampel luefter() throws TimeoutException, NotConnectedException {
            industrialQuadRelay.setValue(1 | industrialQuadRelay.getValue());
            return this;
        }

        public Ampel luefterAus() throws TimeoutException, NotConnectedException {
            industrialQuadRelay.setValue(~1 & industrialQuadRelay.getValue());
            return this;
        }

        public Ampel rot() throws TimeoutException, NotConnectedException {
            industrialQuadRelay.setValue(1 << 1 | industrialQuadRelay.getValue());
            return this;
        }

        public Ampel rotAus() throws TimeoutException, NotConnectedException {
            industrialQuadRelay.setValue(~(1 << 1) & industrialQuadRelay.getValue());
            return this;
        }

        public Ampel gruen() throws TimeoutException, NotConnectedException {
            industrialQuadRelay.setValue(1 << 3 | industrialQuadRelay.getValue());
            return this;
        }

        public Ampel gruenAus() throws TimeoutException, NotConnectedException {
            industrialQuadRelay.setValue(~(1 << 3) & industrialQuadRelay.getValue());
            return this;
        }

        public Ampel gelb() throws TimeoutException, NotConnectedException {
            industrialQuadRelay.setValue(1 << 2 | industrialQuadRelay.getValue());
            return this;
        }

        public Ampel gelbAus() throws TimeoutException, NotConnectedException {
            industrialQuadRelay.setValue(~(1 << 2) & industrialQuadRelay.getValue());
            return this;
        }
    }
