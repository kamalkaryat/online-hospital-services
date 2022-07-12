package com.k2dev.hospital.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.k2dev.hospital.model.dto.TestOrder;
import com.k2dev.hospital.util.TestStatus;

public interface TestOrderRepository extends JpaRepository<TestOrder, String> {
	List<TestOrder> findAllByPatientPatientId(String patientId);

	@Transactional
	@Modifying(clearAutomatically = true)
	@Query("UPDATE TestOrder to set to.status= :status where to.orderId= :id")
	void updateTestStatus(@Param("id") String id, @Param("status") TestStatus status);
	
	List<TestOrder> findAllByLabHospitalHospitalId(String id);
}
