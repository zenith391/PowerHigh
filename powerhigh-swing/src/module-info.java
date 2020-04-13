/**
 * Swing rendering and audio module. Automatically imports <code>powerhigh-core</code> if not present.
 */
module powerhigh.swing {
	requires transitive java.desktop;
	requires transitive java.scripting;
	requires transitive powerhigh.core;
	
	exports org.powerhigh.swing;
	exports org.powerhigh.swing.audio;
}