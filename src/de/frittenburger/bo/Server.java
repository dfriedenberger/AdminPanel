package de.frittenburger.bo;

import de.frittenburger.core.I18n;

public class Server	extends TextInput {
		
		@Override
		public boolean mustEscape() {
			return false;
		}
		
		@Override
		public String getDefaultDescription() {
			return I18n.tr("Server");
		}

	
		
}
