package com.icare.db;

import com.icare.db.entities.DrugsDictionary;
import com.icare.db.entities.TreatmentRecord;

import java.io.Serializable;
import java.util.Objects;

public class TreatmentRecordDrugId implements Serializable {
    private Long id;
    private Long treatmentRecord;
    private Long drug;

    // default constructor
    public TreatmentRecordDrugId() {
    }

    public TreatmentRecordDrugId(Long id, Long treatmentRecord, Long drug) {
        this.id = id;
        this.treatmentRecord = treatmentRecord;
        this.drug = drug;
    }

    // equals() and hashCode()
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TreatmentRecordDrugId that = (TreatmentRecordDrugId) o;

        if (!Objects.equals(treatmentRecord, that.treatmentRecord))
            return false;
        return Objects.equals(drug, that.drug);
    }

    @Override
    public int hashCode() {
        int result = treatmentRecord != null ? treatmentRecord.hashCode() : 0;
        result = 31 * result + (drug != null ? drug.hashCode() : 0);
        return result;
    }
}