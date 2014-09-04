/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mysql.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author krommydakisp
 */
@Entity
@Table(name = "threat")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Threat.findAll", query = "SELECT t FROM Threat t"),
    @NamedQuery(name = "Threat.findByThreatId", query = "SELECT t FROM Threat t WHERE t.threatId = :threatId")})
public class Threat implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "threat_id")
    private Integer threatId;
    @Lob
    @Column(name = "suspicious_ip")
    private String suspiciousIp;

    public Threat() {
    }

    public Threat(Integer threatId) {
        this.threatId = threatId;
    }

    public Integer getThreatId() {
        return threatId;
    }

    public void setThreatId(Integer threatId) {
        this.threatId = threatId;
    }

    public String getSuspiciousIp() {
        return suspiciousIp;
    }

    public void setSuspiciousIp(String suspiciousIp) {
        this.suspiciousIp = suspiciousIp;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (threatId != null ? threatId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Threat)) {
            return false;
        }
        Threat other = (Threat) object;
        if ((this.threatId == null && other.threatId != null) || (this.threatId != null && !this.threatId.equals(other.threatId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mysql.entities.Threat[ threatId=" + threatId + " ]";
    }
    
}
