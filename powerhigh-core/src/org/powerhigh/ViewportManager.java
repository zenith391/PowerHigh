package org.powerhigh;

import java.awt.Rectangle;
import java.util.HashMap;
import java.util.Map;

import org.powerhigh.graphics.Interface;

public abstract class ViewportManager {

	protected Map<String, Object> props = new HashMap<>();
	
	public abstract Rectangle getViewport(Interface win);
	
	public Map<String, Object> getSpecialProperties() {
		return props;
	}

}
