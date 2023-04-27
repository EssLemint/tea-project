package com.lemint.tea.community.attach;

import com.lemint.tea.entity.Attach;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttachRepository extends JpaRepository<Attach, Long>, AttachCustomRepository{
}
