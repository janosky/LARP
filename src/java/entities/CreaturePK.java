/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author aejan
 */
@Embeddable
public class CreaturePK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 5)
    @Column(name = "CREATURE_ID")
    private String creatureId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 5)
    @Column(name = "SCENARIO_ID")
    private String scenarioId;

    public CreaturePK() {
    }

    public CreaturePK(String creatureId, String scenarioId) {
        this.creatureId = creatureId;
        this.scenarioId = scenarioId;
    }

    public String getCreatureId() {
        return creatureId;
    }

    public void setCreatureId(String creatureId) {
        this.creatureId = creatureId;
    }

    public String getScenarioId() {
        return scenarioId;
    }

    public void setScenarioId(String scenarioId) {
        this.scenarioId = scenarioId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (creatureId != null ? creatureId.hashCode() : 0);
        hash += (scenarioId != null ? scenarioId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CreaturePK)) {
            return false;
        }
        CreaturePK other = (CreaturePK) object;
        if ((this.creatureId == null && other.creatureId != null) || (this.creatureId != null && !this.creatureId.equals(other.creatureId))) {
            return false;
        }
        if ((this.scenarioId == null && other.scenarioId != null) || (this.scenarioId != null && !this.scenarioId.equals(other.scenarioId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.CreaturePK[ creatureId=" + creatureId + ", scenarioId=" + scenarioId + " ]";
    }
    
}
