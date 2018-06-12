/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author aejan
 */
@Entity
@Table(name = "creature")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Creature.findAll", query = "SELECT c FROM Creature c")
    , @NamedQuery(name = "Creature.findByCreatureId", query = "SELECT c FROM Creature c WHERE c.creatureId = :creatureId")
    , @NamedQuery(name = "Creature.findByCreatureName", query = "SELECT c FROM Creature c WHERE c.creatureName = :creatureName")
    , @NamedQuery(name = "Creature.findByCreatureType", query = "SELECT c FROM Creature c WHERE c.creatureType = :creatureType")
    , @NamedQuery(name = "Creature.findByFrequency", query = "SELECT c FROM Creature c WHERE c.frequency = :frequency")
    , @NamedQuery(name = "Creature.findByRandomMonster", query = "SELECT c FROM Creature c WHERE c.randomMonster = :randomMonster")
    , @NamedQuery(name = "Creature.findByTerrain", query = "SELECT c FROM Creature c WHERE c.terrain = :terrain")
    , @NamedQuery(name = "Creature.findByDescription", query = "SELECT c FROM Creature c WHERE c.description = :description")
    , @NamedQuery(name = "Creature.findByBackground", query = "SELECT c FROM Creature c WHERE c.background = :background")
    , @NamedQuery(name = "Creature.findByRoleplay", query = "SELECT c FROM Creature c WHERE c.roleplay = :roleplay")
    , @NamedQuery(name = "Creature.findBySpecialAttacks", query = "SELECT c FROM Creature c WHERE c.specialAttacks = :specialAttacks")
    , @NamedQuery(name = "Creature.findBySpecialDefenses", query = "SELECT c FROM Creature c WHERE c.specialDefenses = :specialDefenses")})
public class Creature implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 5)
    @Column(name = "CREATURE_ID")
    private String creatureId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 25)
    @Column(name = "CREATURE_NAME")
    private String creatureName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 25)
    @Column(name = "CREATURE_TYPE")
    private String creatureType;
    @Size(max = 10)
    @Column(name = "FREQUENCY")
    private String frequency;
    @Size(max = 3)
    @Column(name = "RANDOM_MONSTER")
    private String randomMonster;
    @Size(max = 25)
    @Column(name = "TERRAIN")
    private String terrain;
    @Size(max = 100)
    @Column(name = "DESCRIPTION")
    private String description;
    @Size(max = 100)
    @Column(name = "BACKGROUND")
    private String background;
    @Size(max = 100)
    @Column(name = "ROLEPLAY")
    private String roleplay;
    @Size(max = 100)
    @Column(name = "SPECIAL_ATTACKS")
    private String specialAttacks;
    @Size(max = 100)
    @Column(name = "SPECIAL_DEFENSES")
    private String specialDefenses;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "creatureId")
    private Collection<Archtypes> archtypesCollection;
    @JoinColumn(name = "SCENARIO_ID", referencedColumnName = "SCENARIO_ID")
    @ManyToOne(optional = false)
    private Scenario scenarioId;

    public Creature() {
    }

    public Creature(String creatureId) {
        this.creatureId = creatureId;
    }

    public Creature(String creatureId, String creatureName, String creatureType) {
        this.creatureId = creatureId;
        this.creatureName = creatureName;
        this.creatureType = creatureType;
    }

    public String getCreatureId() {
        return creatureId;
    }

    public void setCreatureId(String creatureId) {
        this.creatureId = creatureId;
    }

    public String getCreatureName() {
        return creatureName;
    }

    public void setCreatureName(String creatureName) {
        this.creatureName = creatureName;
    }

    public String getCreatureType() {
        return creatureType;
    }

    public void setCreatureType(String creatureType) {
        this.creatureType = creatureType;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getRandomMonster() {
        return randomMonster;
    }

    public void setRandomMonster(String randomMonster) {
        this.randomMonster = randomMonster;
    }

    public String getTerrain() {
        return terrain;
    }

    public void setTerrain(String terrain) {
        this.terrain = terrain;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public String getRoleplay() {
        return roleplay;
    }

    public void setRoleplay(String roleplay) {
        this.roleplay = roleplay;
    }

    public String getSpecialAttacks() {
        return specialAttacks;
    }

    public void setSpecialAttacks(String specialAttacks) {
        this.specialAttacks = specialAttacks;
    }

    public String getSpecialDefenses() {
        return specialDefenses;
    }

    public void setSpecialDefenses(String specialDefenses) {
        this.specialDefenses = specialDefenses;
    }

    @XmlTransient
    public Collection<Archtypes> getArchtypesCollection() {
        return archtypesCollection;
    }

    public void setArchtypesCollection(Collection<Archtypes> archtypesCollection) {
        this.archtypesCollection = archtypesCollection;
    }

    public Scenario getScenarioId() {
        return scenarioId;
    }

    public void setScenarioId(Scenario scenarioId) {
        this.scenarioId = scenarioId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (creatureId != null ? creatureId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Creature)) {
            return false;
        }
        Creature other = (Creature) object;
        if ((this.creatureId == null && other.creatureId != null) || (this.creatureId != null && !this.creatureId.equals(other.creatureId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Creature[ creatureId=" + creatureId + " ]";
    }
    
}