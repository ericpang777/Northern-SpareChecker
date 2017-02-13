package sparechecker;

import java.awt.Color;
import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;

class StripeRenderer extends DefaultListCellRenderer {
	
	/**
	 * Colours every odd row, a light gray colour.
	 */
	@Override
	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,
			boolean cellHasFocus) {

		JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
		
		if (index % 2 == 0) {
			label.setBackground(new Color(234, 234, 234));
		}
		return label;
	}
}
