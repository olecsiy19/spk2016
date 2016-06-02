import java.util.ArrayList;
import java.util.List;

public class TourManager {

	// ��������� �������� ���
	private List<City> destinationCities = new ArrayList<City>();

	// ��������� ������� ����
	public void add(City city) {
		destinationCities.add(city);
	}

	// ��������� ����
	public City get(int index) {
		return (City) destinationCities.get(index);
	}

	// ��������� �-�� ��� �� ��������� ������
	public int size() {
		return destinationCities.size();
	}
}