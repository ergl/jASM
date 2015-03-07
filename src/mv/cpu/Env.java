/*
 * This file is part of jASM.
 *
 * jASM is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * jASM is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with jASM.  If not, see <http://www.gnu.org/licenses/>.
 */

package mv.cpu;

import java.util.Hashtable;
import java.util.Map;
import java.util.Optional;

public class Env {

    private Hashtable<Integer, String> env;

    public Env() {
        env = new Hashtable<Integer, String>();
    }

    public Optional<String> find(Integer key) {
        String val = env.get(key);
        return val == null ? Optional.empty() : Optional.of(val);
    }

    public void set(Integer key, String value) {
        env.put(key, value);
    }

    public Optional<Integer> find(String value) {
        for (Map.Entry<Integer, String> entry : env.entrySet()) {
            if (value.equalsIgnoreCase(entry.getValue())) {
                return Optional.of(entry.getKey());
            }
        }

        return Optional.empty();
    }

    @Override
    public String toString() {
        return env.toString();
    }
}
