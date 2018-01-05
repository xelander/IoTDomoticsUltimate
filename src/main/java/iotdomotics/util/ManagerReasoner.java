package iotdomotics.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import com.hp.hpl.jena.rdf.model.InfModel;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.reasoner.Reasoner;

import iotdomotics.ontology.LinkOntology;

public class ManagerReasoner {
	
	public synchronized static InfModel startReasoner(Reasoner reasoner, LinkOntology link){
		reasoner.setDerivationLogging(false);
		return ModelFactory.createInfModel(reasoner, link.getModel());
	}
	
	public synchronized static InfModel startReasonerWithWrite(Reasoner reasoner, LinkOntology link){
		InfModel infModel = ModelFactory.createInfModel(reasoner, link.getModel());
		OutputStreamWriter out = null;
		try {
			out = new OutputStreamWriter(new FileOutputStream("E:\\WorkSpace\\IoTDomoticsUltimate\\inferred.owl"), "UTF-8");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		infModel.write(out,"RDF/XML");
		return infModel;
		
	}
}
