package jsfclasses;

import entities.Recept;
import jsfclasses.util.JsfUtil;
import jsfclasses.util.JsfUtil.PersistAction;
import sessions.ReceptFacade;

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

@ManagedBean(name = "receptController")
@ViewScoped
public class ReceptController implements Serializable {

    @EJB
    private sessions.ReceptFacade ejbFacade;
    private List<Recept> items = null;
    private Recept selected;

    public ReceptController() {
    }

    public Recept getSelected() {
        return selected;
    }

    public void setSelected(Recept selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private ReceptFacade getFacade() {
        return ejbFacade;
    }

    public Recept prepareCreate() {
        getFacade().refresh_em();
        selected = new Recept();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        getFacade().refresh_em();
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("ReceptCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        getFacade().refresh_em();
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("ReceptUpdated"));
    }

    public void destroy() {
        getFacade().refresh_em();
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("ReceptDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Recept> getItems() {
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

    public List<Recept> getItemsAvailableSelectMany() {
        getFacade().refresh_em();
        return getFacade().findAll();
    }

    public List<Recept> getItemsAvailableSelectOne() {
        getFacade().refresh_em();
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Recept.class)
    public static class ReceptControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ReceptController controller = (ReceptController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "receptController");
            controller.getFacade().refresh_em();
            return controller.getFacade().find(getKey(value));
        }

        java.lang.String getKey(String value) {
            java.lang.String key;
            key = value;
            return key;
        }

        String getStringKey(java.lang.String value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Recept) {
                Recept o = (Recept) object;
                return getStringKey(o.getDocid());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Recept.class.getName()});
                return null;
            }
        }

    }

}
