package iotdomotics.main;

import java.lang.reflect.Field;
import java.util.AbstractMap;
import java.util.HashMap;

import org.mindswap.pellet.jena.PelletReasonerFactory;

import com.hp.hpl.jena.ontology.OntProperty;
import com.hp.hpl.jena.ontology.OntResource;
import com.hp.hpl.jena.rdf.model.InfModel;
import com.hp.hpl.jena.reasoner.Reasoner;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;

import iotdomotics.ontology.LinkOntology;
import iotdomotics.ontology.ManagerOntology;
import iotdomotics.ontology.OntologyInterface;
import iotdomotics.util.ManagerReasoner;
import iotdomotics.util.Measure;
import iotdomotics.util.NS;

public class ResourceManager {
	private static volatile ResourceManager instance;
	private static final Object lock = new Object();
	private LinkOntology link = null;
	private InfModel infModel = null;
	private Reasoner pellet = null;
	private OntologyInterface ontology = null;
	private HashMap<String, AbstractMap.SimpleEntry<OntProperty, String>> map = null;

	private ResourceManager(){

	}

	public static ResourceManager getInstance() {
		ResourceManager r = instance;
		if (r == null) {
			synchronized (lock) {    // While we were waiting for the lock, another 
				r = instance;        // thread may have instantiated the object.
				if (r == null) {  
					r = new ResourceManager();
					instance = r;
				}
			}
		}
		return r;
	}

	public synchronized void createContext(){
		if(link == null)
			link = new LinkOntology("E:\\WorkSpace\\IoTDomoticsUltimate\\IoTDomotics.owl", "http://www.semanticweb.org/gurui/ontologies/2017/9/IoTDomotics");
		if(pellet == null)
			pellet = PelletReasonerFactory.theInstance().create();
		if(ontology == null)
			ontology = new ManagerOntology();
		if(infModel == null)
			infModel = ManagerReasoner.startReasonerWithWrite(pellet, this.link);
		if(map == null)
			fillMapProp();
	}
	
	private void fillMapProp(){
		map = new HashMap<String, AbstractMap.SimpleEntry<OntProperty, String>>();
		for(Field f: Measure.class.getDeclaredFields()){
			OntProperty prop = this.link.getModel().getOntProperty(NS.BASE + f.getName());
			ExtendedIterator<? extends OntResource> ranges = null;
			try{
				ranges = prop.listRange();
			} catch(NullPointerException n){
				System.out.println(f.getName());
			}
			try{
				String type = ranges.next().toString();
				map.put(f.getName(), new AbstractMap.SimpleEntry<OntProperty, String>(prop, type));
			} catch(IllegalArgumentException e){
				e.printStackTrace();
			}
		}
	}
	
	public InfModel startReasoning() {
		// TODO Auto-generated method stub
		infModel = ManagerReasoner.startReasoner(this.pellet, this.link);
		return infModel;
	}

	public LinkOntology getLink() {
		return link;
	}

	public InfModel getInfModel() {
		return infModel;
	}

	public Reasoner getPellet() {
		return pellet;
	}

	public OntologyInterface getOntology() {
		return ontology;
	}

	public HashMap<String, AbstractMap.SimpleEntry<OntProperty, String>> getMap() {
		return map;
	}
	
	
}
