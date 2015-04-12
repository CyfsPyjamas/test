package edu.neu.cs5200.msn.orm.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import edu.neu.cs5200.msn.orm.models.Site;

public class SiteDAO {

	EntityManagerFactory factory = Persistence.createEntityManagerFactory("SiteProject");
	EntityManager em = factory.createEntityManager();
	
	public Site findSite(int siteId)
	{
		return em.find(Site.class, siteId);
	}
	
	public List<Site> findAllSites()
	{
		Query query = em.createQuery("select site from Site site");
		return (List<Site>)query.getResultList();
	}
	
	public List<Site> updateSite(int siteId, Site site)
	{
		em.getTransaction().begin();
		site.setId(siteId);
		em.merge(site);
		em.getTransaction().commit();
		Query query = em.createQuery("select site from Site site");
		return (List<Site>)query.getResultList();
	}
	public List<Site> removeSite(int siteId) {
		em.getTransaction().begin();
		Site site = em.find(Site.class, siteId);
		em.remove(site);
		em.getTransaction().commit();
		Query query = em.createQuery("select site from Site site");
		return (List<Site>)query.getResultList();
	}
	
	public List<Site> createSite(Site site)
	{
		em.getTransaction().begin();
		em.persist(site);
		em.getTransaction().commit();
		Query query = em.createQuery("select site from Site site");
		return (List<Site>)query.getResultList();
	}
}