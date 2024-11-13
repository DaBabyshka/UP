import java.util.ArrayList;
import java.util.List;

public class CertificateService {
    private List<Certificate> certificates = new ArrayList<>();

    public List<Certificate> getAllCertificates() {
        return certificates;
    }

    public void addCertificate(String id, String type, String requesterName) {
        certificates.add(new Certificate(id, type, requesterName));
    }

    public Certificate findCertificateById(String id) {
        return certificates.stream()
                .filter(cert -> cert.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public void updateCertificateStatus(String id, String newStatus) {
        Certificate certificate = findCertificateById(id);
        if (certificate != null) {
            certificate.setStatus(newStatus);
        }
    }
}
