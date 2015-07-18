package jsfclasses;

import entities.SupplierProducts;
import jsfclasses.util.JsfUtil;
import jsfclasses.util.JsfUtil.PersistAction;
import sessions.SupplierProductsFacade;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@ManagedBean(name = "supplierProductsController")
@ViewScoped
public class SupplierProductsController implements Serializable {

    @EJB
    private sessions.SupplierProductsFacade ejbFacade;
    private List<SupplierProducts> items = null;
    private SupplierProducts selected;

    public SupplierProductsController() {
    }

    public SupplierProducts getSelected() {
        return selected;
    }

    public void setSelected(SupplierProducts selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
        selected.getSupplierProductsPK().setBarcode(selected.getProduct().getBarcode());
        selected.getSupplierProductsPK().setSupplierid(selected.getSupplier().getSupplierid());
        selected.getSupplierProductsPK().setUserid(selected.getUsersAchat().getUserid());
    }

    protected void initializeEmbeddableKey() {
        selected.setSupplierProductsPK(new entities.SupplierProductsPK());
    }

    private SupplierProductsFacade getFacade() {
        return ejbFacade;
    }

    public SupplierProducts prepareCreate() {
        selected = new SupplierProducts();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("SupplierProductsCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("SupplierProductsUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("SupplierProductsDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<SupplierProducts> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    getFacade().edit(selected);
                } else {
                    getFacade().remove(selected);
                }
                JsfUtil.addSuccessMessage(successMessage);
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            }
        }
    }

    public List<SupplierProducts> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<SupplierProducts> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = SupplierProducts.class)
    public static class SupplierProductsControllerConverter implements Converter {

        private static final String SEPARATOR = "#";
        private static final String SEPARATOR_ESCAPED = "\\#";

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            SupplierProductsController controller = (SupplierProductsController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "supplierProductsController");
            return controller.getFacade().find(getKey(value));
        }

        entities.SupplierProductsPK getKey(String value) {
            entities.SupplierProductsPK key;
            String values[] = value.split(SEPARATOR_ESCAPED);
            key = new entities.SupplierProductsPK();
            key.setBarcode(values[0]);
            key.setSupplierid(Integer.parseInt(values[1]));
            key.setUserid(values[2]);
            return key;
        }

        String getStringKey(entities.SupplierProductsPK value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value.getBarcode());
            sb.append(SEPARATOR);
            sb.append(value.getSupplierid());
            sb.append(SEPARATOR);
            sb.append(value.getUserid());
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof SupplierProducts) {
                SupplierProducts o = (SupplierProducts) object;
                return getStringKey(o.getSupplierProductsPK());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), SupplierProducts.class.getName()});
                return null;
            }
        }

    }

}
