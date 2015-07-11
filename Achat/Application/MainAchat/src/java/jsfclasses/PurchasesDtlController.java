package jsfclasses;

import entities.PurchasesDtl;
import jsfclasses.util.JsfUtil;
import jsfclasses.util.JsfUtil.PersistAction;
import sessions.PurchasesDtlFacade;

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

@ManagedBean(name = "purchasesDtlController")
@ViewScoped
public class PurchasesDtlController implements Serializable {

    @EJB
    private sessions.PurchasesDtlFacade ejbFacade;
    private List<PurchasesDtl> items = null;
    private PurchasesDtl selected;

    public PurchasesDtlController() {
    }

    public PurchasesDtl getSelected() {
        return selected;
    }

    public void setSelected(PurchasesDtl selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
        //selected.getPurchasesDtlPK().setDocid(selected.getPurchases().getDocid());
         selected.getPurchasesDtlPK().setDocid(selected.getPurchases().getDocid());
        selected.getPurchasesDtlPK().setBarcode(selected.getProduct().getBarcode());
    }

    protected void initializeEmbeddableKey() {
        selected.setPurchasesDtlPK(new entities.PurchasesDtlPK());
    }

    private PurchasesDtlFacade getFacade() {
        return ejbFacade;
    }

    public PurchasesDtl prepareCreate() {
        getFacade().bob123();
        selected = new PurchasesDtl();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        getFacade().bob123();
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("PurchasesDtlCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        getFacade().bob123();
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("PurchasesDtlUpdated"));
    }

    public void destroy() {
        getFacade().bob123();
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("PurchasesDtlDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<PurchasesDtl> getItems() {
        getFacade().bob123();
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        getFacade().bob123();
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

    public List<PurchasesDtl> getItemsAvailableSelectMany() {
        getFacade().bob123();
        return getFacade().findAll();
    }

    public List<PurchasesDtl> getItemsAvailableSelectOne() {
        getFacade().bob123();
        return getFacade().findAll();
    }

    @FacesConverter(forClass = PurchasesDtl.class)
    public static class PurchasesDtlControllerConverter implements Converter {

        private static final String SEPARATOR = "#";
        private static final String SEPARATOR_ESCAPED = "\\#";

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            PurchasesDtlController controller = (PurchasesDtlController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "purchasesDtlController");
            controller.getFacade().bob123();
            return controller.getFacade().find(getKey(value));
        }

        entities.PurchasesDtlPK getKey(String value) {
            entities.PurchasesDtlPK key;
            String values[] = value.split(SEPARATOR_ESCAPED);
            key = new entities.PurchasesDtlPK();
            key.setDocid(values[0]);
            key.setBarcode(values[1]);
            return key;
        }

        String getStringKey(entities.PurchasesDtlPK value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value.getDocid());
            sb.append(SEPARATOR);
            sb.append(value.getBarcode());
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof PurchasesDtl) {
                PurchasesDtl o = (PurchasesDtl) object;
                return getStringKey(o.getPurchasesDtlPK());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), PurchasesDtl.class.getName()});
                return null;
            }
        }

    }

}
