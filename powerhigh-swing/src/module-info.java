/**
 * Swing rendering and audio module. Needs powerhigh-core
 */
module powerhigh.swing {
	
	requires transitive java.desktop;
	requires transitive java.scripting;
	requires powerhigh.core;
	
	exports org.powerhigh.swing;
	exports org.powerhigh.swing.audio;
	
}