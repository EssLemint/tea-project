package com.lemint.tea.util;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 임시 비밀번호 생성 util
 * */
@Slf4j
@Component
@RequiredArgsConstructor
public class TempPasswordUtil {
  /**
   * 임시 비밀번호 발급
   * Random Vs SecureRandom : 둘의 난수는 seed라는 기준 값이 발생시키는데
   * Random은 시스템 기준 시간으로 진행하고
   * SecureRandom은 OS에 있는 임의의 데이터로 진행한다. Random을 사용하면서 seed가 되는 시간이 동일하면 결국 똑같은 값을 생성하는 상황이 방샐하기에
   * 사용을 지양하는것이 맞다. 또한 Random은 48bit, SecureRandom은 128bit이며 결정론적 알고리즘이 도입되어있어 예측 가능성이 낟은 난수 값이 생성된다.
   * */

  private static final char[] rndAllCharacters = new char[]{
      //number
      '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
      //uppercase
      'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
      'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
      //lowercase
      'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
      'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
      //special symbols
      '@', '$', '!', '%', '*', '?', '&'
  };

  private static final char[] numberCharacters = new char[] {
      '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'
  };

  private static final char[] uppercaseCharacters = new char[] {
      'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
      'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'
  };

  private static final char[] lowercaseCharacters = new char[] {
      'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
      'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'
  };

  private static final char[] specialSymbolCharacters = new char[] {
      '@', '$', '!', '%', '*', '?', '&'
  };


  //조건에 맞는 비밀번호 생성하기
  public String getRandomPassword(int length) {
    SecureRandom random = new SecureRandom();
    StringBuilder stringBuilder = new StringBuilder();

    List<Character> passwordCharacters = new ArrayList<>();

    //각 최소 1글자씩 조건에 맞는 비밀번호 각각 생성
    int numberCharactersLength = numberCharacters.length;
    passwordCharacters.add(numberCharacters[random.nextInt(numberCharactersLength)]);

    int uppercaseCharactersLength = uppercaseCharacters.length;
    passwordCharacters.add(uppercaseCharacters[random.nextInt(uppercaseCharactersLength)]);

    int lowercaseCharactersLength = lowercaseCharacters.length;
    passwordCharacters.add(lowercaseCharacters[random.nextInt(lowercaseCharactersLength)]);

    int specialSymbolCharactersLength = specialSymbolCharacters.length;
    passwordCharacters.add(specialSymbolCharacters[random.nextInt(specialSymbolCharactersLength)]);


    //최소 조건 만족 이후의 추가 길이들 생성
    int rndAllCharactersLength = rndAllCharacters.length;
    for (int i = 0; i < length-4; i++) {
      passwordCharacters.add(rndAllCharacters[random.nextInt(rndAllCharactersLength)]);
    }

    //패턴 방지를 위한 shuffle
    Collections.shuffle(passwordCharacters);

    for (Character character : passwordCharacters) {
      stringBuilder.append(character);
    }

    return stringBuilder.toString();
  }

}
