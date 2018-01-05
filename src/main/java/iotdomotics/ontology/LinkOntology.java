package iotdomotics.ontology;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.rdf.model.ModelFactory;

public class LinkOntology {
	private String SOURCE;
	private String URI;
	private OntModel model;

	public LinkOntology(String source, String uri){
		this.SOURCE = "file:" + source;
    	this.URI = uri;
    	this.model = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM, null);
    	this.model.getDocumentManager().addAltEntry(URI, SOURCE);
    	this.model.read(URI, "RDF/XML");		
	}

	public String getSOURCE() {
		return SOURCE;
	}

	public void setSOURCE(String source) {
		SOURCE = source;
	}

	public String getURI() {
		return URI;
	}

	public void setURI(String uRI) {
		URI = uRI;
	}

	public OntModel getModel() {
		return model;
	}
}
