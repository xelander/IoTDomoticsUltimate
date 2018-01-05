package iotdomotics.ontology;

import java.lang.reflect.Field;
import java.util.AbstractMap;
import java.util.HashMap;

import com.hp.hpl.jena.datatypes.xsd.XSDDatatype;
import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntProperty;
import com.hp.hpl.jena.ontology.OntResource;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.rdf.model.InfModel;
import com.hp.hpl.jena.reasoner.Reasoner;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;

import iotdomotics.util.ManagerReasoner;
import iotdomotics.util.Measure;
import iotdomotics.util.NS;

public class ManagerOntology implements OntologyInterface{
	private LinkOntology link;
	private InfModel infModel;
	private Reasoner pellet;
	private HashMap<String, AbstractMap.SimpleEntry<OntProperty, String>> map;

	public ManagerOntology(LinkOntology link, Reasoner reasoner){
		this.link = link;
		this.pellet = reasoner;
		this.infModel = ManagerReasoner.startReasonerWithWrite(reasoner, this.link);
		map = new HashMap<String, AbstractMap.SimpleEntry<OntProperty, String>>();
		fillMapProp();
	}

	private void fillMapProp(){
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

	public Individual createIndividual(OntModel ont, String id, Measure m) {
		// TODO Auto-generated method stub
		Individual individual = this.link.getModel().createIndividual(NS.BASE + id, link.getModel().getOntProperty(NS.BASE + "Thing"));
		for(Field f: m.getClass().getDeclaredFields()){
			try {
				if(f.get(m) == null)
					continue;
			} catch (IllegalArgumentException | IllegalAccessException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			OntProperty prop = this.map.get(f.getName()).getKey();

			String type = this.map.get(f.getName()).getValue();
			try{
				switch(type){
				case NS.XSD + "boolean":
					individual.addProperty(prop, f.get(m).toString(), XSDDatatype.XSDboolean);
				break;
				case NS.XSD + "float":
					individual.addProperty(prop, f.get(m).toString(), XSDDatatype.XSDfloat);
				break;
				case NS.XSD + "integer":
					individual.addProperty(prop, f.get(m).toString(), XSDDatatype.XSDinteger);
				break;
				default:
					individual.addProperty(prop, f.get(m).toString(), XSDDatatype.XSDstring);
					break;
				}
			} catch(IllegalArgumentException | IllegalAccessException e){
				e.printStackTrace();
			}
		}
		return individual;
	}

	public InfModel startReasoning() {
		// TODO Auto-generated method stub
		this.infModel = ManagerReasoner.startReasoner(this.pellet, this.link);
		return this.infModel;
	}

	public String interviewOntClassMeasure(String id) {
		// TODO Auto-generated method stub
		String query = "SELECT ?val WHERE {<" + NS.BASE + id + "> <"
				+ NS.RDF + "type> ?x. ?x <"
				+ NS.RDFS + "measure> ?val}";
		Query queryEx = QueryFactory.create(query);
		QueryExecution exec = QueryExecutionFactory.create(queryEx, this.infModel);

		com.hp.hpl.jena.query.ResultSet set = exec.execSelect();
		if(set.hasNext())
			return set.next().get("val").toString();
		else
			return null;
	}

	public void updateProperty(OntProperty property, String value) {
		// TODO Auto-generated method stub

	}

	public String interviewOntActuatorCmd(String id) {
		// TODO Auto-generated method stub
		String query = "SELECT ?val WHERE {<" + NS.BASE + id + "> <"
				+ NS.RDF + "type> ?x. ?x <"
				+ NS.RDFS + "cmd> ?val}";
		Query queryEx = QueryFactory.create(query);
		QueryExecution exec = QueryExecutionFactory.create(queryEx, this.infModel);

		com.hp.hpl.jena.query.ResultSet set = exec.execSelect();
		if(set.hasNext())
			return set.next().get("val").toString();
		else
			return null;
	}

}




