package helper;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ListModel;
import javax.swing.event.ListDataListener;

import dataholders.Circle;

public class CircleListModel implements ListModel<Circle[]>{

	public static final int SELECTED_ALL = 0;
	List<ListDataListener> listeners;
	Circle[] circles;
	
	public CircleListModel(int circleCount)
	{
		listeners = new ArrayList<ListDataListener>();
		circles = new Circle[circleCount];
		for(int i = 0; i < circleCount; i++)
			circles[i] = new Circle(new Coordinate2D(0,0), i+1);
	}
	
	@Override
	public void addListDataListener(ListDataListener listener) {
		listeners.add(listener);
	}
	
	@Override
	public void removeListDataListener(ListDataListener listener) {
		listeners.remove(listener);		
	}

	@Override
	public Circle[] getElementAt(int idx) {
		if(idx == SELECTED_ALL)
			return circles;
		else
		{
			return new Circle[]{circles[idx-1]};
		}
	}

	@Override
	public int getSize() {
		return circles.length;
	}
}
