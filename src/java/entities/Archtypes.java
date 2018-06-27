/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author aejan
 */
@Entity
@Table(name = "archtypes")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Archtypes.findAll", query = "SELECT a FROM Archtypes a")
    , @NamedQuery(name = "Archtypes.findByArchytypeId", query = "SELECT a FROM Archtypes a WHERE a.archytypeId = :archytypeId")
    , @NamedQuery(name = "Archtypes.findByArchytyeClass", query = "SELECT a FROM Archtypes a WHERE a.archytyeClass = :archytyeClass")
    , @NamedQuery(name = "Archtypes.findByHitPoints", query = "SELECT a FROM Archtypes a WHERE a.hitPoints = :hitPoints")
    , @NamedQuery(name = "Archtypes.findByHtP", query = "SELECT a FROM Archtypes a WHERE a.htP = :htP")
    , @NamedQuery(name = "Archtypes.findByALevel", query = "SELECT a FROM Archtypes a WHERE a.aLevel = :aLevel")
    , @NamedQuery(name = "Archtypes.findByArmorType", query = "SELECT a FROM Archtypes a WHERE a.armorType = :armorType")
    , @NamedQuery(name = "Archtypes.findByArmorWorn", query = "SELECT a FROM Archtypes a WHERE a.armorWorn = :armorWorn")
    , @NamedQuery(name = "Archtypes.findByWeaponType", query = "SELECT a FROM Archtypes a WHERE a.weaponType = :weaponType")
    , @NamedQuery(name = "Archtypes.findByDamage", query = "SELECT a FROM Archtypes a WHERE a.damage = :damage")
    , @NamedQuery(name = "Archtypes.findByDescription", query = "SELECT a FROM Archtypes a WHERE a.description = :description")})
public class Archtypes implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 5)
    @Column(name = "ARCHYTYPE_ID")
    private String archytypeId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 25)
    @Column(name = "ARCHYTYE_CLASS")
    private String archytyeClass;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 25)
    @Column(name = "HIT_POINTS")
    private String hitPoints;
    @Column(name = "HT_P")
    private Integer htP;
    @Basic(optional = false)
    @NotNull
    @Column(name = "A_LEVEL")
    private int aLevel;
    @Size(max = 25)
    @Column(name = "ARMOR_TYPE")
    private String armorType;
    @Size(max = 25)
    @Column(name = "ARMOR_WORN")
    private String armorWorn;
    @Size(max = 25)
    @Column(name = "WEAPON_TYPE")
    private String weaponType;
    @Size(max = 25)
    @Column(name = "DAMAGE")
    private String damage;
    @Size(max = 300)
    @Column(name = "DESCRIPTION")
    private String description;
    @JoinColumns({
    @JoinColumn(name = "CREATURE_ID", referencedColumnName = "CREATURE_ID"),
    @JoinColumn(name = "CREATURE_ID", referencedColumnName = "CREATURE_ID") })
    @ManyToOne(optional = false)
    private Creature creatureId;

    public Archtypes() {
    }

    public Archtypes(String archytypeId) {
        this.archytypeId = archytypeId;
    }

    public Archtypes(String archytypeId, String archytyeClass, String hitPoints, int aLevel) {
        this.archytypeId = archytypeId;
        this.archytyeClass = archytyeClass;
        this.hitPoints = hitPoints;
        this.aLevel = aLevel;
    }

    public String getArchytypeId() {
        return archytypeId;
    }

    public void setArchytypeId(String archytypeId) {
        this.archytypeId = archytypeId;
    }

    public String getArchytyeClass() {
        return archytyeClass;
    }

    public void setArchytyeClass(String archytyeClass) {
        this.archytyeClass = archytyeClass;
    }

    public String getHitPoints() {
        return hitPoints;
    }

    public void setHitPoints(String hitPoints) {
        this.hitPoints = hitPoints;
    }

    public Integer getHtP() {
        return htP;
    }

    public void setHtP(Integer htP) {
        this.htP = htP;
    }

    public int getALevel() {
        return aLevel;
    }

    public void setALevel(int aLevel) {
        this.aLevel = aLevel;
    }

    public String getArmorType() {
        return armorType;
    }

    public void setArmorType(String armorType) {
        this.armorType = armorType;
    }

    public String getArmorWorn() {
        return armorWorn;
    }

    public void setArmorWorn(String armorWorn) {
        this.armorWorn = armorWorn;
    }

    public String getWeaponType() {
        return weaponType;
    }

    public void setWeaponType(String weaponType) {
        this.weaponType = weaponType;
    }

    public String getDamage() {
        return damage;
    }

    public void setDamage(String damage) {
        this.damage = damage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Creature getCreatureId() {
        return creatureId;
    }

    public void setCreatureId(Creature creatureId) {
        this.creatureId = creatureId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (archytypeId != null ? archytypeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Archtypes)) {
            return false;
        }
        Archtypes other = (Archtypes) object;
        if ((this.archytypeId == null && other.archytypeId != null) || (this.archytypeId != null && !this.archytypeId.equals(other.archytypeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Archtypes[ archytypeId=" + archytypeId + " ]";
    }
    
}
