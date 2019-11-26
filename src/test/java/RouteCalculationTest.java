import core.Line;
import core.Station;
import java.util.ArrayList;
import java.util.List;
import junit.framework.TestCase;

public class RouteCalculationTest extends TestCase {

  private RouteCalculator calculatorTest;
  private static StationIndex stationIndex;

  // ручное задание метро с целью независимости от считывания JSON файла

  @Override
  protected void setUp() {

    stationIndex = new StationIndex();
    Line firstLine = new Line(0, "red");
    Line secondLine= new Line(1, "blue");
    Line thirdLine = new Line(2, "green");

	  RouteCalculationTest.stationIndex.addLine(firstLine);
	  RouteCalculationTest.stationIndex.addLine(secondLine);
	  RouteCalculationTest.stationIndex.addLine(thirdLine);

	  stationIndex.addStation(new Station("A", RouteCalculationTest.stationIndex.getLine(0)));
	  stationIndex.addStation(new Station("B", RouteCalculationTest.stationIndex.getLine(0)));
	  stationIndex.addStation(new Station("C", RouteCalculationTest.stationIndex.getLine(0)));
	  stationIndex.addStation(new Station("D", RouteCalculationTest.stationIndex.getLine(0)));
	  stationIndex.addStation(new Station("E", RouteCalculationTest.stationIndex.getLine(0)));
	  stationIndex.addStation(new Station("F", RouteCalculationTest.stationIndex.getLine(0)));

	  stationIndex.addStation(new Station("G", RouteCalculationTest.stationIndex.getLine(1)));
	  stationIndex.addStation(new Station("H", RouteCalculationTest.stationIndex.getLine(1)));
	  stationIndex.addStation(new Station("I", RouteCalculationTest.stationIndex.getLine(1)));
	  stationIndex.addStation(new Station("J", RouteCalculationTest.stationIndex.getLine(1)));
	  stationIndex.addStation(new Station("K", RouteCalculationTest.stationIndex.getLine(1)));

	  stationIndex.addStation(new Station("L", RouteCalculationTest.stationIndex.getLine(2)));
	  stationIndex.addStation(new Station("M", RouteCalculationTest.stationIndex.getLine(2)));
	  stationIndex.addStation(new Station("N", RouteCalculationTest.stationIndex.getLine(2)));
	  stationIndex.addStation(new Station("O", RouteCalculationTest.stationIndex.getLine(2)));

	  firstLine.addStation(new Station("A", stationIndex.getLine(0)));
	  firstLine.addStation(new Station("B", stationIndex.getLine(0)));
	  firstLine.addStation(new Station("C", stationIndex.getLine(0)));
	  firstLine.addStation(new Station("D", stationIndex.getLine(0)));
	  firstLine.addStation(new Station("E", stationIndex.getLine(0)));
	  firstLine.addStation(new Station("F", stationIndex.getLine(0)));

	  secondLine.addStation(new Station("G", stationIndex.getLine(1)));
	  secondLine.addStation(new Station("H", stationIndex.getLine(1)));
	  secondLine.addStation(new Station("I", stationIndex.getLine(1)));
	  secondLine.addStation(new Station("J", stationIndex.getLine(1)));
	  secondLine.addStation(new Station("K", stationIndex.getLine(1)));

	  thirdLine.addStation(new Station("L", stationIndex.getLine(2)));
	  thirdLine.addStation(new Station("M", stationIndex.getLine(2)));
	  thirdLine.addStation(new Station("N", stationIndex.getLine(2)));
	  thirdLine.addStation(new Station("O", stationIndex.getLine(2)));

    List<Station> firstConnection = new ArrayList<>();
    firstConnection.add(RouteCalculationTest.stationIndex.getStation("D", 0));
    firstConnection.add(RouteCalculationTest.stationIndex.getStation("H", 1));

    List<Station> secondConnection = new ArrayList<>();
    secondConnection.add(RouteCalculationTest.stationIndex.getStation("K", 1));
    secondConnection.add(RouteCalculationTest.stationIndex.getStation("M", 2));

    RouteCalculationTest.stationIndex.addConnection(firstConnection);
    RouteCalculationTest.stationIndex.addConnection(secondConnection);

    calculatorTest = new RouteCalculator(RouteCalculationTest.stationIndex);

  }

  @Override
  protected void tearDown() {
  }

  public void test_get_shortest_route_to_same_station() {

    List<Station> expected = new ArrayList<>();
    expected.add(stationIndex.getStation("A"));

    Station fromOnTheLine = stationIndex.getStation("A");
    Station toOnTheLine = stationIndex.getStation("A");
    List<Station> actualOnTheLine = calculatorTest.getShortestRoute(fromOnTheLine, toOnTheLine);
    assertEquals(expected, actualOnTheLine);
  }

  public void test_get_shortest_route_stations_next_to_each_other_on_single_line() {

    List<Station> expected = new ArrayList<>();
    expected.add(stationIndex.getStation("A"));
    expected.add(stationIndex.getStation("B"));

    Station fromOnTheLine = stationIndex.getStation("A");
    Station toOnTheLine = stationIndex.getStation("B");
    List<Station> actualOnTheLine = calculatorTest.getShortestRoute(fromOnTheLine, toOnTheLine);
    assertEquals(expected, actualOnTheLine);
  }

  public void test_get_shortest_route_on_the_line() {

    List<Station> expected = new ArrayList<>();
    expected.add(stationIndex.getStation("A"));
    expected.add(stationIndex.getStation("B"));
    expected.add(stationIndex.getStation("C"));
    expected.add(stationIndex.getStation("D"));
    expected.add(stationIndex.getStation("E"));
    expected.add(stationIndex.getStation("F"));

    Station fromOnTheLine = stationIndex.getStation("A");
    Station toOnTheLine = stationIndex.getStation("F");
    List<Station> actualOnTheLine = calculatorTest.getShortestRoute(fromOnTheLine, toOnTheLine);
    assertEquals(expected, actualOnTheLine);
  }

  public void test_get_shortest_route_one_connection() {

    List<Station> expected = new ArrayList<>();
    expected.add(stationIndex.getStation("A"));
    expected.add(stationIndex.getStation("B"));
    expected.add(stationIndex.getStation("C"));
    expected.add(stationIndex.getStation("D"));
    expected.add(stationIndex.getStation("H"));
    expected.add(stationIndex.getStation("I"));

    Station fromOnTheLine = stationIndex.getStation("A");
    Station toOnTheLine = stationIndex.getStation("I");
    List<Station> actualOnTheLine = calculatorTest.getShortestRoute(fromOnTheLine, toOnTheLine);
    assertEquals(expected, actualOnTheLine);
  }

  public void test_get_shortest_route_two_connections() {

    List<Station> expected = new ArrayList<>();
    expected.add(stationIndex.getStation("A"));
    expected.add(stationIndex.getStation("B"));
    expected.add(stationIndex.getStation("C"));
    expected.add(stationIndex.getStation("D"));
    expected.add(stationIndex.getStation("H"));
    expected.add(stationIndex.getStation("I"));
    expected.add(stationIndex.getStation("J"));
    expected.add(stationIndex.getStation("K"));
    expected.add(stationIndex.getStation("M"));
    expected.add(stationIndex.getStation("N"));
    expected.add(stationIndex.getStation("O"));

    Station fromOnTheLine = stationIndex.getStation("A");
    Station toOnTheLine = stationIndex.getStation("O");
    List<Station> actualOnTheLine = calculatorTest.getShortestRoute(fromOnTheLine, toOnTheLine);
    assertEquals(expected, actualOnTheLine);
  }

  public void test_duration_route_to_same_station() {

    double expected = 0;

    Station fromOnTheLine = stationIndex.getStation("A");
    Station toOnTheLine = stationIndex.getStation("A");
    List<Station> actualOnTheLine = calculatorTest.getShortestRoute(fromOnTheLine, toOnTheLine);
    double actual = RouteCalculator.calculateDuration(actualOnTheLine);
    assertEquals(expected, actual);
  }

  public void test_duration_route_stations_next_to_each_other_on_single_line() {

    double expected = 2.5;

    Station fromOnTheLine = stationIndex.getStation("A");
    Station toOnTheLine = stationIndex.getStation("B");
    List<Station> actualOnTheLine = calculatorTest.getShortestRoute(fromOnTheLine, toOnTheLine);
    double actual = RouteCalculator.calculateDuration(actualOnTheLine);
    assertEquals(expected, actual);
  }

  public void test_duration_route_on_the_line() {

    double expected = 12.5;

    Station fromOnTheLine = stationIndex.getStation("A");
    Station toOnTheLine = stationIndex.getStation("F");
    List<Station> actualOnTheLine = calculatorTest.getShortestRoute(fromOnTheLine, toOnTheLine);
    double actual = RouteCalculator.calculateDuration(actualOnTheLine);
    assertEquals(expected, actual);
  }

  public void test_duration_route_one_connection() {

    double expected = 13.5;

    Station fromOnTheLine = stationIndex.getStation("A");
    Station toOnTheLine = stationIndex.getStation("I");
    List<Station> actualOnTheLine = calculatorTest.getShortestRoute(fromOnTheLine, toOnTheLine);
    double actual = RouteCalculator.calculateDuration(actualOnTheLine);
    assertEquals(expected, actual);
  }

  public void test_duration_route_two_connections() {

    double expected = 27;

    Station fromOnTheLine = stationIndex.getStation("A");
    Station toOnTheLine = stationIndex.getStation("O");
    List<Station> actualOnTheLine = calculatorTest.getShortestRoute(fromOnTheLine, toOnTheLine);
    double actual = RouteCalculator.calculateDuration(actualOnTheLine);
    assertEquals(expected, actual);
  }

}

/**
 *
 *                                 Line 2
 *                                   G
 *                                   |
 *                                   |
 *      line 1       A -- B -- C -- D/H -- E -- F
 *                                   |
 *                                   |
 *                                   I
 *                                   |
 *                                   |
 *                                   J
 *                                   |
 *                                   |
 *      line 3                 L -- M/K -- N -- O
 *
 */
