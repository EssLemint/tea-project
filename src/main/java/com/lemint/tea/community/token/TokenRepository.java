package com.lemint.tea.community.token;

import com.lemint.tea.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<Token, Long>, TokenRepositoryCustom {
}
