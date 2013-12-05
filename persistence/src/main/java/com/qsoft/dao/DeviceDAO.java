package com.qsoft.dao;

import com.qsoft.model.common.Device;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * User: quynhtq
 * Date: 12/4/13
 */
public interface DeviceDAO extends JpaRepository<Device,Long>
{
}
