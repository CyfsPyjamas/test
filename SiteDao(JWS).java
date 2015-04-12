package edu.neu.cs5200.msn.orm.dao;

import java.io.*;
import java.util.*;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.*;
import javax.xml.transform.*;
import javax.xml.transform.stream.*;
import javax.persistence.*;
import javax.print.attribute.standard.Media;

@Path("/site")
public class SiteDao {
	
	EntityManagerFactory factory = Persistence.createEntityManagerFactory("XSLT_JPA");
	EntityManager em = null;
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Site findSite(@PathParam("id") int siteId) {
		Site site = null;
		
		em = factory.createEntityManager();
		em.getTransaction().begin();
		
		site = em.find(Site.class, siteId);
		
		em.getTransaction().commit();
		em.close();
		
		return site;
	}
	
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Site> findAllSites() {
		List<Site> sites = new ArrayList<Site>();
		
		em = factory.createEntityManager();
		em.getTransaction().begin();
		
		Query query = em.createNamedQuery("findAllSites");
		sites = query.getResultList();
		
		em.getTransaction().commit();
		em.close();

		return sites;
	}
	
	@Update
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<Site> updateSite(@PathParam("id") int siteId, Site site) {
		List<Site> sites = new ArrayList<Site>();

		em = factory.createEntityManager();
		em.getTransaction().begin();

		director.setId(siteId);
		em.merge(site);
		
		Query query = em.createNamedQuery("findAllSites");
		sites = query.getResultList();
		
		em.getTransaction().commit();
		em.close();
		return sites;
	}
	
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Site> deleteSite(@PathParam("id") int siteId) {
		List<Site> sites = new ArrayList<Site>();

		Site site = null;
		
		em = factory.createEntityManager();
		em.getTransaction().begin();
		
		site = em.find(Site.class, siteId);
		em.remove(site);
		
		Query query = em.createNamedQuery("findAllSites");
		sites = query.getResultList();
		
		em.getTransaction().commit();
		em.close();
		
		return sites;
	}
	
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<Site> createSite(Site site) {
		List<Site> sites = new ArrayList<Site>();

		em = factory.createEntityManager();
		em.getTransaction().begin();

		em.persist(site);
		Query query = em.createNamedQuery("findAllSites");
		sites = query.getResultList();
		
		em.getTransaction().commit();
		em.close();
		return sites;
	}
	
	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<Site> updateSite(@PathParam("id") int siteId, Site site) {
		List<Site> sites = new ArrayList<Site>();

		em = factory.createEntityManager();
		em.getTransaction().begin();

		director.setId(siteId);
		em.merge(site);
		
		Query query = em.createNamedQuery("findAllSites");
		sites = query.getResultList();
		
		em.getTransaction().commit();
		em.close();
		return sites;
	}
	
	public void exportSitesToXmlFile(Sites sites, String xmlFileName) {
		File xmlFile = new File(xmlFileName);
		try {
			JAXBContext jaxb = JAXBContext.newInstance(Sites.class);
			Marshaller marshaller = jaxb.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.marshal(sites, xmlFile);
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void convertXmlFileToOutputFile(
			String sitesXmlFileName,
			String outputFileName,
			String xsltFileName)
	{
		File inputXmlFile = new File(sitesXmlFileName);
		File outputXmlFile = new File(outputFileName);
		File xsltFile = new File(xsltFileName);
		
		StreamSource source = new StreamSource(inputXmlFile);
		StreamSource xslt    = new StreamSource(xsltFile);
		StreamResult output = new StreamResult(outputXmlFile);
		
		TransformerFactory factory = TransformerFactory.newInstance();
		try {
			Transformer transformer = factory.newTransformer(xslt);
			transformer.transform(source, output);
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		SiteDao dao = new SiteDao();
		
		Site site = dao.findSite(1);
		dao.removeSite(2);
		List<Site> sites = dao.findAllSites();		
		Sites theSites = new Sites();
		theSites.setDirectors(sites);
		
		dao.exportSitesToXmlFile(theSites, "xml/sites.xml");

	}
}