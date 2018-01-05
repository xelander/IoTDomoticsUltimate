package iotdomotics.ontology;

import java.lang.reflect.Field;

import com.hp.hpl.jena.datatypes.xsd.XSDDatatype;
import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntProperty;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.rdf.model.InfModel;

import iotdomotics.main.ResourceManager;
import iotdomotics.util.Measure;
import iotdomotics.util.NS;

public class ManagerOntology implements OntologyInterface{

	public ManagerOntology(){

	}

	public Individual createIndividual(OntModel ont, String id, Measure m) {
		// TODO Auto-generated method stub
		Individual individual = ResourceManager.getInstance().getLink().getModel().createIndividual(NS.BASE + id, ResourceManager.getInstance().getLink().getModel().getOntProperty(NS.BASE + "Thing"));
		for(Field f: m.getClass().getDeclaredFields()){
			try {
				if(f.get(m) == null)
					continue;
			} catch (IllegalArgumentException | IllegalAccessException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			OntProperty prop = ResourceManager.getInstance().getMap().get(f.getName()).getKey();

			String type = ResourceManager.getInstance().getMap().get(f.getName()).getValue();
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
		return ResourceManager.getInstance().startReasoning();
	}

	public String interviewOntClassMeasure(String id) {
		// TODO Auto-generated method stub
		String query = "SELECT ?val WHERE {<" + NS.BASE + id + "> <"
				+ NS.RDF + "type> ?x. ?x <"
				+ NS.RDFS + "measure> ?val}";
		Query queryEx = QueryFactory.create(query);
		QueryExecution exec = QueryExecutionFactory.create(queryEx, ResourceManager.getInstance().getInfModel());

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
		QueryExecution exec = QueryExecutionFactory.create(queryEx, ResourceManager.getInstance().getInfModel());

		com.hp.hpl.jena.query.ResultSet set = exec.execSelect();
		if(set.hasNext())
			return set.next().get("val").toString();
		else
			return null;
	}

}




