package com.example.petsupplies.core.backend.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import com.example.petsupplies.core.backend.dao.BaseDAO;
import com.example.petsupplies.core.backend.dao.ProductDAO;
import com.example.petsupplies.core.backend.entity.ProductEntity;
import com.example.petsupplies.core.common.constants.Constants;
import com.example.petsupplies.core.model.ProductSearchFilter;

/**
 * ProductDAOImpl is used add/edit/delete/display product
 * @author Jeetendra
 * @version 1.0
 * @since 2015-06-15
 */

public class ProductDAOImpl extends BaseDAO implements ProductDAO
{

   /**
    * @param ProductSearchFilter
    * @return list of products
    */
   public List<ProductEntity> getProducts(ProductSearchFilter searchFilter)
   {
      logger.info("ProductDAOImpl :: getProducts method called");
      TypedQuery<ProductEntity> searchQuery = entityManager.createNamedQuery(Constants.QueryConstants.SEARCH_PRODUCTS, ProductEntity.class);
      try
      {
         searchQuery.setParameter("categoryName", searchFilter.getCategoryName());
         searchQuery.setParameter("productName", searchFilter.getProductName());
         searchQuery.setParameter("description", searchFilter.getDescription());
         return searchQuery.getResultList();
      }
      catch (NoResultException e)
      {
         logger.log(Level.SEVERE, "ProductDAOImpl :: getAllProducts ends with NoResultException :: ", e);

      }
      catch (PersistenceException ex)
      {
         logger.log(Level.SEVERE, "ProductDAOImpl :: getAllProducts ends with PersistenceException :: ", ex);
      }
      return new ArrayList<ProductEntity>();
   }

   /**
    * @return list of products
    */
   public List<ProductEntity> getAllProducts()
   {
      logger.info("ProductDAOImpl :: getAllProducts method called");
      TypedQuery<ProductEntity> queryResults = entityManager.createNamedQuery(Constants.QueryConstants.SHOW_ALL_PRODUCTS, ProductEntity.class);
      try
      {
         return queryResults.getResultList();
      }
      catch (NoResultException e)
      {
         logger.log(Level.SEVERE, "ProductDAOImpl :: getAllProducts ends with NoResultException :: ", e);

      }
      catch (PersistenceException ex)
      {
         logger.log(Level.SEVERE, "ProductDAOImpl :: getAllProducts ends with PersistenceException :: ", ex);
      }
      return new ArrayList<ProductEntity>();
   }
   
   /**
    * @return list of products
    */
   public ProductEntity getProduct(Long productId)
   {
      logger.info("ProductDAOImpl :: getAllProducts method called");
      ProductEntity productEntity = null;
      try
      {
    	  productEntity = entityManager.find(ProductEntity.class, productId);
      }
      catch (NoResultException e)
      {
         logger.log(Level.SEVERE, "ProductDAOImpl :: getAllProducts ends with NoResultException :: ", e);

      }
      catch (PersistenceException ex)
      {
         logger.log(Level.SEVERE, "ProductDAOImpl :: getAllProducts ends with PersistenceException :: ", ex);
      }
      return productEntity;
   }

   /**
    * @param product
    * @return boolean This method is used to create product.
    */
   public boolean createProduct(ProductEntity productEntity)
   {
      logger.info("ProductDAOImpl :: createProduct method called");
      try
      {
    	 utx.begin();
         entityManager.persist(productEntity);
         utx.commit();
         return true;
      }catch (PersistenceException e)
      {
         logger.log(Level.SEVERE, "ProductDAOImpl :: createProduct ends with PersistenceException :: ", e);
      }catch (Exception e)
      {
          logger.log(Level.SEVERE, "ProductDAOImpl :: createProduct ends with Exception :: ", e);
       }

      return false;
   }

   /**
    * @param product
    * @return boolean This method is used to delete product from the DB
    */
   public boolean deleteProduct(ProductEntity productEntity)
   {
      logger.info("ProductDAOImpl :: deleteProduct method called");
      try
      {
         ProductEntity productInDB = entityManager.find(ProductEntity.class, productEntity.getProductId());
         entityManager.remove(productInDB);
         return true;
      }
      catch (PersistenceException e)
      {
         logger.log(Level.SEVERE, "ProductDAOImpl :: deleteProduct ends with PersistenceException :: ", e);
      }
      return false;
   }

   /**
    * @param product
    * @return boolean This method is used to edit product from the DB
    */
   public boolean editProduct(ProductEntity productEntity)
   {
      logger.info("ProductDAOImpl :: editProduct method called");
      try
      {
    	 utx.begin();
         entityManager.merge(productEntity);
         utx.commit();
         return true;
      }
      catch (PersistenceException e)
      {
         logger.log(Level.SEVERE, "ProductDAOImpl :: editProduct ends with PersistenceException :: ", e);
      }
      catch (Exception e)
      {
          logger.log(Level.SEVERE, "ProductDAOImpl :: editProduct ends with Exception :: ", e);
       }
      return false;
   }

}
