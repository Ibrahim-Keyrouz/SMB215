package jsfclasses;

import entities.StkPrd;
import jsfclasses.util.JsfUtil;
import jsfclasses.util.JsfUtil.PersistAction;
import sessions.StkPrdFacade;

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

@ManagedBean(name = "stkPrdController")
@ViewScoped
public class StkPrdController implements Serializable {

    @EJB
    private sessions.StkPrdFacade ejbFacade;
    private List<StkPrd> items = null;
    private StkPrd selected;

    public StkPrdController() {
    }

    public StkPrd getSelected() {
        return selected;
    }

    public void setSelected(StkPrd selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
        selected.getStkPrdPK().setBarcode(selected.getProduct().getBarcode());
        selected.getStkPrdPK().setSiteid(selected.getSites().getSiteid());
    }

    protected void initializeEmbeddableKey() {
        selected.setStkPrdPK(new entities.StkPrdPK());
    }

    private StkPrdFacade getFacade() {
        return ejbFacade;
    }

    public StkPrd prepareCreate() {
        getFacade().refresh_em();
        selected = new StkPrd();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
         getFacade().refresh_em();
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("StkPrdCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
         getFacade().refresh_em();
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("StkPrdUpdated"));
    }

    public void destroy() {
         getFacade().refresh_em();
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("StkPrdDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<StkPrd> getItems() {
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

    public List<StkPrd> getItemsAvailableSelectMany() {
         getFacade().refresh_em();
        return getFacade().findAll();
    }

    public List<StkPrd> getItemsAvailableSelectOne() {
         getFacade().refresh_em();
        return getFacade().findAll();
    }

    @FacesConverter(forClass = StkPrd.class)
    public static class StkPrdControllerConverter implements Converter {

        private static final String SEPARATOR = "#";
        private static final String SEPARATOR_ESCAPED = "\\#";

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            StkPrdController controller = (StkPrdController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "stkPrdController");
            controller.getFacade().refresh_em();
            return controller.getFacade().find(getKey(value));
        }

        entities.StkPrdPK getKey(String value) {
            entities.StkPrdPK key;
            String values[] = value.split(SEPARATOR_ESCAPED);
            key = new entities.StkPrdPK();
            key.setSiteid(values[0]);
            key.setBarcode(values[1]);
            return key;
        }

        String getStringKey(entities.StkPrdPK value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value.getSiteid());
            sb.append(SEPARATOR);
            sb.append(value.getBarcode());
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof StkPrd) {
                StkPrd o = (StkPrd) object;
                return getStringKey(o.getStkPrdPK());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), StkPrd.class.getName()});
                return null;
            }
        }

    }

}
