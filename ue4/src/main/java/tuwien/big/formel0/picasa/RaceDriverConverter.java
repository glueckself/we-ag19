/*
 * Copyright 2013 srdj.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package tuwien.big.formel0.picasa;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.persistence.EntityManager;
import tuwien.big.formel0.utilities.Utility;

/**
 *
 * @author srdj
 */
@ManagedBean
@RequestScoped
@FacesConverter("tuwien.big.formel0.picasa.RaceDriverConverter")
public class RaceDriverConverter
implements Converter{
    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) throws ConverterException {
        EntityManager em = Utility.getEntityManagerFactory().createEntityManager();
        em.getTransaction().begin();
        RaceDriver rd=em.find(RaceDriver.class,string);
        em.getTransaction().rollback();
        em.close();
        return (Object)rd;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) throws ConverterException {
        if(o == null)
            return "";
        
        if (!(o instanceof RaceDriver)) {
            throw new ConverterException();
        }
        
        RaceDriver rd = (RaceDriver)o;
        
        return rd.getName();
    }
    
}
