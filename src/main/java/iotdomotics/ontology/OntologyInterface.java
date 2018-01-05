package iotdomotics.ontology;

import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntProperty;
import com.hp.hpl.jena.rdf.model.InfModel;

import iotdomotics.util.Measure;

public interface OntologyInterface {
	Individual createIndividual(OntModel ont, String id, Measure m);
	InfModel startReasoning();
	String interviewOntClassMeasure(String id);
	void updateProperty(OntProperty property, String value);
	String interviewOntActuatorCmd(String id);
}
