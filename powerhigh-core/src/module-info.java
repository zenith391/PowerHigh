/**
 * Core module of the library. Need to be used with swing/opengl module to works.
 */
module powerhigh.core {
	exports org.powerhigh.objects;
	exports org.powerhigh.tools;
	exports org.powerhigh.ui;
	exports org.powerhigh.utils;
	exports org.powerhigh.graphics;
	exports org.powerhigh;
	exports org.powerhigh.graphics.renderers;
	exports org.powerhigh.game;
	exports org.powerhigh.audio;
	exports org.powerhigh.utils.debug;
	exports org.powerhigh.graphics.renderers.lightning;
	exports org.powerhigh.input;

	requires java.desktop;
	requires java.xml;
}