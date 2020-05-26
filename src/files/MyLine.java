package files;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.AbstractMap;
import java.util.Collections;
import java.util.List;

/**
 * Třídá reprezentující linku autobusu
 *
 * @author Filip Václavík (xvacla30)
 * @author Michal Zobaník (xzoban01)
 */
public class MyLine implements Line {

	private String id;
	List <AbstractMap.SimpleImmutableEntry<Street,Stop>> map_list = new ArrayList <AbstractMap.SimpleImmutableEntry<Street,Stop>>();
	private List<Detour> detours;

	/**
	 * Prázdný konstruktor pro vytvoření bezejmenné linky
	 */
	public MyLine(){
		this.id = "";
	}

	/**
	 * Konstruktor pro vytvoření linky
	 * 
	 * @param id jméno linky
	 */
	public MyLine(String id){
		this.id = id;
	}

	/**
	 * 
	 * @return jméno linky
	 */
	public String getId(){
		return this.id;
	}

	/**
	 * Nastaví jméno linky
	 * 
	 * @param id linky
	 */
	public void setId(String id){
		this.id = id;
	}

	/**
	 * Získá první zastávku na lince
	 * 
	 * @return fistStop
	 */
	public Stop getFirstStop(){
		return this.map_list.get(0).getValue();
	}

	/**
	 * Získá poslední zastávku na lince
	 * 
	 * @return lastStop
	 */
	public Stop getLastStop(){
		return this.map_list.get(map_list.size()-1).getValue();
	}

	/**
	 * Vloží zastávku do linky podle toho zda leží na ulici, která se nachází na lince, vrátí true nebo false podle
	 * toho zda to bylo úspěšné
	 * 
	 * @param stop přidávaní zastávka 
	 * @return true když se přidala, false když se nepřidala
	 */
	public boolean addStop(Stop stop) {
        Street s = stop.getStreet();
		if(!(map_list.isEmpty())){
			AbstractMap.SimpleImmutableEntry<Street,Stop> end = map_list.get(map_list.size()-1);
			Street end_a = end.getKey(); //získá klíč poslední položky v seznamu
			if(s != null){
				if(!(s.follows(end_a))) { //pokud není null a je tam návaznost false jinak tru a přidá zastávku
					return false;
				}
				map_list.add(new AbstractMap.SimpleImmutableEntry<Street,Stop>(s, stop));
				return true;
			}//pokud null přidá zastávku
			map_list.add(new AbstractMap.SimpleImmutableEntry<Street,Stop>(s, stop));
			return true;
		}//přidá zastávku
		map_list.add(new AbstractMap.SimpleImmutableEntry<Street,Stop>(s, stop));
		return true;
	}
	
	/**
	 * Vloží ulici do linky, vrátí true nebo false podle
	 * toho zda to bylo úspěšné
	 * 
	 * @param street přidávaná ulice
	 * @return true když se přidala, false když se nepřidala
	 */
	public boolean addStreet(Street street) {
		if(!(map_list.isEmpty())){
			Street s = map_list.get(map_list.size()-1).getKey();
			if(!(street.follows(s))){
				return false;
			}//přidá ulici do seznamu
			map_list.add(new AbstractMap.SimpleImmutableEntry<Street,Stop>(street, null));
			return true;
		}
		return false;
	}

	/**
	 * Vrátí linku s ulicemi a zastávkami
	 * 
	 * @return linka
	 */
	public java.util.List<java.util.AbstractMap.SimpleImmutableEntry<Street,Stop>> getRoute() {
		List <AbstractMap.SimpleImmutableEntry<Street,Stop>> lists = Collections.unmodifiableList(map_list);
		return lists;
	}
	
	/**
	 * Vytvoří linku pouze se jménem
	 * 
	 * @param id jméno linky
	 * @return linka
	 */
	public static Line defaultLine(java.lang.String id) {
		Line line = new MyLine(id);
		return line;
	}
	
	@Override
    public String toString(){
		try {
			return "" + this.id + " Po\u010d\u00e1tek: " + getFirstStop().getId();
		} catch (Exception e) {
			return "" + this.id + "";
		} 
    }

	/**
	 * Najde souřadnice, které mají společné zadané cesty
	 *
	 * @param street1 Cesta 1
	 * @param street2 Cesta 2
	 * @return Souřadnice, které mají cesty společné
	 */
    private Coordinate getEqualCoord(Street street1, Street street2) {
		if (street1.begin().equals(street2.begin()) || street1.begin().equals(street2.end()))
			return street1.begin();
		else if (street1.end().equals(street2.end()) || street1.end().equals(street2.begin()))
			return street1.end();
		else
			return null;
	}

	/**
	 * Zvýrazní linku na mapě
	 */
    public void highlight() {
		this.map_list.forEach(pair -> {
			pair.getKey().select();
			if (pair.getValue() != null)
				pair.getValue().select();
		});
	}

	/**
	 * Najde indexy první a poslední zastávky na lince. Pokud mezi zastávkami není uzavřená cesta bez objížďky
	 * jedná se o první a poslední zastávku na lince. Jinak jsou to zastávky mezi kterými není zavřená cesta bez objížďky.
	 * Vrací null pokud nejde takto najít 2 zastávky.
	 *
	 * @return indexy první a poslední zastávky linkys
	 */
	private int[] getStopIdxs() {
    	int [] idxs = new int[2];
    	boolean haveStart = false;
    	boolean haveEnd = false;
    	for (int i = 0; i < map_list.size(); i++) {
    		//najde první zastávku
    		if (!haveStart) {
				if (map_list.get(i).getValue() != null && map_list.get(i).getKey().isOpen()) {
					haveStart = true;
					idxs[0] = i;
				}
			} else {
    			//hledá poslední zastávku
    			if (map_list.get(i).getValue() != null) {
    				if (map_list.get(i).getKey().isOpen()) {
    					haveEnd = true;
    					idxs[1] = i;
					} else if (!hasDetour(map_list.get(i).getKey())) { //ulice je zavřená a nemá objížďku
    					if (haveEnd)
    						return idxs;
    					else
    						return null;
					}
				} else {
					if (!map_list.get(i).getKey().isOpen() && !hasDetour(map_list.get(i).getKey()) && !haveEnd) {
						return null;
					} else if (!map_list.get(i).getKey().isOpen() && !hasDetour(map_list.get(i).getKey()) && haveEnd) {
						return idxs;
					}
				}
			}
		}

		if (haveEnd)
			return idxs;
		else
			return null;
	}

	/**
	 * Zjistí jestli je pro zadanou cestu definována objížďka
	 *
	 * @param street Ulice pro, kterou se objížďka hledá
	 * @return true, jestli cesta má objížďku
	 */
	private boolean hasDetour(Street street) {
    	return false;
    	/*
    	for (Detour detour : detours) {
    		if (detour.isReplacing(street))
    			return true;
		}

    	return false; */
	}

	/**
	 * Sestaví cestu autobusu. Kontroluje jestli jestli jsou cesty zavřené. V případě, že je nějaká cesta zavřená
	 * přidá cestu objížďky, jestli objížďka existuje, nebo změní konečnou/první zastávku. Ke každé zastávce přidá čas z
	 * jízdního řádu, v případě že se jedná o zlom na cestě k hodnotě času je přiřazena hodnota null.
	 *
	 * @param schedule Jízdí řád autobusu pro danou linku
	 * @return Souřadnice, které reprezentují jednotlivé zlomy na cestě/zastávky a časy kdy se tam autobus má nacházet
	 */
	public List<AbstractMap.SimpleEntry<Coordinate, LocalTime>> getBusRoute(Schedule schedule) {
		List<AbstractMap.SimpleEntry<Coordinate, LocalTime>> route = new ArrayList<>();
		int jump;

		//indexy první a poslední zastávky
		int[] stopIdxs = getStopIdxs();

		if (stopIdxs == null)
			return null;

		for (int i = stopIdxs[0]; i < stopIdxs[1]; i++) {
			if (map_list.get(i).getValue() == null) {
				//přidání cesty
				if (!hasDetour(map_list.get(i).getKey())) { //bez objížďky
					route.add(new AbstractMap.SimpleEntry<>(getEqualCoord(map_list.get(i).getKey(), map_list.get(i + 1).getKey()), null));
				} else { //s objížďkou
					jump = setDetourRoute(map_list.get(i).getKey(), route);
					i += jump;
				}
			} else {
				//přidíní zastávky
				if (!hasDetour(map_list.get(i).getValue().getStreet())) { //bez objížďky
					route.add(new AbstractMap.SimpleEntry<>(map_list.get(i).getValue().getCoordinate(), schedule.getTime(map_list.get(i).getValue())));

					//přidání konce ulice, když se na ulici nenachází další zastávka
					if (map_list.get(i + 1).getValue() == null || (map_list.get(i + 1).getValue() != null && !map_list.get(i + 1).getKey().equals(map_list.get(i).getKey())))
						route.add(new AbstractMap.SimpleEntry<>(getEqualCoord(map_list.get(i).getKey(), map_list.get(i + 1).getKey()), null));
				} else { //s objížďkou
					jump = setDetourRoute(map_list.get(i).getValue().getStreet(), route);
					i += jump;
				}
			}
		}

		//poslední zastávka
		route.add(new AbstractMap.SimpleEntry<>(map_list.get(stopIdxs[1]).getValue().getCoordinate(), schedule.getTime(map_list.get(stopIdxs[1]).getValue())));
		return route;
	}

	private int setDetourRoute(Street street, List<AbstractMap.SimpleEntry<Coordinate, LocalTime>> route) {
		for (Detour detour : detours) {
			if (detour.isReplacing(street)) {
				detour.getRoute(street, route);
				return detour.getJump();
			}
		}

		return 0;
	}

	/**
	 * Získá seznam cest, s obížďkama, pro jednotlivé úseky mezi body v cestě autobusu
	 *
	 * @return Seznam ulic
	 */
	public List<Street> getStreets() {
		List<Street> streets = new ArrayList<>();

		streets.add(map_list.get(0).getKey());

		for (int i = 1; i < map_list.size() - 1; i++) {
			if (map_list.get(i).getValue() == null) {
				//přidání ulice bez zastávky
				streets.add(map_list.get(i).getKey());
			} else {
				//přidání ulice pro úsek ulice za zastávkou
				streets.add(map_list.get(i).getKey());

				//přidání ulice pro úsek před zastávkou, pokud jí nepředchází zastávka na stejné ulici
				if (map_list.get(i - 1).getValue() == null || (map_list.get(i - 1).getValue() != null && !map_list.get(i - 1).getKey().equals(map_list.get(i).getKey())))
					streets.add(map_list.get(i).getKey());
			}
		}

		streets.add(map_list.get(map_list.size() - 1).getKey());

		return streets;
	}

	public void deselect() {
		this.map_list.forEach(pair -> {
			pair.getKey().deselect();
			if (pair.getValue() != null)
				pair.getValue().deselect();
		});
	}
}