package jsfclasses;

import entities.ReceptDtl;
import jsfclasses.util.JsfUtil;
import jsfclasses.util.JsfUtil.PersistAction;
import sessions.ReceptDtlFacade;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.bean.ManagedBean;

import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@ManagedBean(name = "receptDtlController")

@ViewScoped
public class ReceptDtlController implements Serializable {

    @EJB
    private sessions.ReceptDtlFacade ejbFacade;
    private List<ReceptDtl> items = null;
    private ReceptDtl selected;

    public ReceptDtlController() {
    }

    public ReceptDtl getSelected() {
        return selected;
    }

    public void setSelected(ReceptDtl selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
        selected.getReceptDtlPK().setBarcode(selected.getProduct().getBarcode());
        selected.getReceptDtlPK().setDocid(selected.getRecept().getDocid());
    }

    protected void initializeEmbeddableKey() {
        selected.setReceptDtlPK(new entities.ReceptDtlPK());
    }

    private ReceptDtlFacade getFacade() {
        return ejbFacade;
    }

    public ReceptDtl prepareCreate() {
        getFacade().refresh_em();
        selected = new ReceptDtl();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        getFacade().refresh_em();
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("ReceptDtlCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        getFacade().refresh_em();
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("ReceptDtlUpdated"));
    }

    public void destroy() {
        getFacade().refresh_em();
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("ReceptDtlDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<ReceptDtl> getItems() {
        getFacade().refresh_em();
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        getFacade().refresh_em();
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

    public List<ReceptDtl> getItemsAvailableSelectMany() {
        getFacade().refresh_em();
        return getFacade().findAll();
    }

    public List<ReceptDtl> getItemsAvailableSelectOne() {
        getFacade().refresh_em();
        return getFacade().findAll();
    }

    @FacesConverter(forClass = ReceptDtl.class)
    public static class ReceptDtlControllerConverter implements Converter {

        private static final String SEPARATOR = "#";
        private static final String SEPARATOR_ESCAPED = "\\#";

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ReceptDtlController controller = (ReceptDtlController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "receptDtlController");
            controller.getFacade().refresh_em();
            return controller.getFacade().find(getKey(value));
        }

        entities.ReceptDtlPK getKey(String value) {
            entities.ReceptDtlPK key;
            String values[] = value.split(SEPARATOR_ESCAPED);
            key = new entities.ReceptDtlPK();
            key.setDocid(values[0]);
            key.setBarcode(values[1]);
            return key;
        }

        String getStringKey(entities.ReceptDtlPK value) {
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
            if (object instanceof ReceptDtl) {
                ReceptDtl o = (ReceptDtl) object;
                return getStringKey(o.getReceptDtlPK());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), ReceptDtl.class.getName()});
                return null;
            }
        }

    }

}
