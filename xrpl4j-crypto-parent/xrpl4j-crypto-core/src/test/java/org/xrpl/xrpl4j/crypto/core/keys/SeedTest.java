package org.xrpl.xrpl4j.crypto.core.keys;

import static org.assertj.core.api.Assertions.assertThat;

import com.google.common.io.BaseEncoding;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.xrpl.xrpl4j.codec.addresses.UnsignedByteArray;
import org.xrpl.xrpl4j.codec.addresses.VersionType;

/**
 * Unit tests for {@link org.xrpl.xrpl4j.crypto.Seed}.
 */
public class SeedTest {

  private Seed edSeed = Seed.ed25519SeedFromPassphrase(Passphrase.of("hello"));
  private Seed ecSeed = Seed.secp256k1SeedFromPassphrase(Passphrase.of("hello"));

  @Test
  void constructorWithNullSeed() {
    Assertions.assertThrows(NullPointerException.class, () -> {
      Seed nullSeed = null;
      new Seed(nullSeed);
    });
  }

  @Test
  void constructorWithNullUnsignedByteArray() {
    Assertions.assertThrows(NullPointerException.class, () -> {
      UnsignedByteArray nullUba = null;
      new Seed(nullUba);
    });
  }

  @Test
  void testSecp256k1SeedFromNullEntropy() {
    Assertions.assertThrows(NullPointerException.class, () -> {
      Seed.secp256k1SeedFromEntropy(null);
    });
  }

  @Test
  void testEd25519SeedFromEntropyNullEntropy() {
    Assertions.assertThrows(NullPointerException.class, () -> {
      Seed.ed25519SeedFromEntropy(null);
    });
  }

  @Test
  void testEd25519SeedFromPassphraseWithNull() {
    Assertions.assertThrows(NullPointerException.class, () -> {
      Seed.ed25519SeedFromPassphrase(null);
    });
  }

  @Test
  void testSecp256k1SeedFromPassphraseWithNull() {
    Assertions.assertThrows(NullPointerException.class, () -> {
      Seed.secp256k1SeedFromPassphrase(null);
    });
  }

  @Test
  public void testEd25519SeedFromPassphrase() {
    assertThat(edSeed.decodedSeed().type().get()).isEqualTo(VersionType.ED25519);
    assertThat(BaseEncoding.base64().encode(edSeed.decodedSeed().bytes().toByteArray()))
      .isEqualTo("m3HSJL1i83hdltRq0+o9cw==");
    assertThat(edSeed.isDestroyed()).isFalse();
    edSeed.destroy();
    assertThat(edSeed.isDestroyed()).isTrue();
  }

  @Test
  public void testSecp256k1SeedFromPassphrase() {
    assertThat(ecSeed.decodedSeed().type().get()).isEqualTo(VersionType.SECP256K1);
    assertThat(BaseEncoding.base64().encode(ecSeed.decodedSeed().bytes().toByteArray()))
      .isEqualTo("m3HSJL1i83hdltRq0+o9cw==");
    assertThat(ecSeed.isDestroyed()).isFalse();
    ecSeed.destroy();
    assertThat(ecSeed.isDestroyed()).isTrue();
  }

  @Test
  void testEquals() {
    assertThat(edSeed).isEqualTo(edSeed);
    assertThat(ecSeed).isEqualTo(ecSeed);
    assertThat(edSeed).isNotEqualTo(ecSeed);
    assertThat(ecSeed).isNotEqualTo(edSeed);
    assertThat(ecSeed).isNotEqualTo(new Object());
  }

  @Test
  void testHashCode() {
    assertThat(edSeed.hashCode()).isEqualTo(edSeed.hashCode());
    assertThat(ecSeed.hashCode()).isEqualTo(ecSeed.hashCode());
  }

  @Test
  void testToString() {
    assertThat(edSeed.toString()).isEqualTo("Seed{value=[redacted], destroyed=false}");
  }

}