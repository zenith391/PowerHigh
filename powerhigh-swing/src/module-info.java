/**
 * Swing rendering and audio module. Needs powerhigh-core
 */
module powerhigh.swing {
	
	requires transitive java.desktop;
	requires transitive powerhigh.core;
	
	exports org.powerhigh.swing;
	exports org.powerhigh.swing.audio;
	
}