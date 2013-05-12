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
package formel0api;

import javax.faces.bean.SessionScoped;

/**
 * Class representing a Formel 0 game
 */
@SessionScoped
public class Field {
    private boolean oilSpit;
    
    public Field(boolean _oilSpit) {
        oilSpit=_oilSpit;
    }
    
    public boolean isOilSpit() {
        return oilSpit;
    }
}
