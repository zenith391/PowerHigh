/**
 * Swing rendering and audio module. Needs powerhigh-core
 */
module powerhigh.swing {
	
	requires transitive java.desktop;
	requires transitive java.scripting;
	requires powerhigh.core;
	requires jdk.scripting.nashorn; // TODO: remove, but still be able to access ScriptObjectMirror or something like that
	
	exports org.powerhigh.swing;
	exports org.powerhigh.swing.audio;
	
}