@commentaire
Feature: Ajout d'un commentaire à un sondage
  As an utilisateur
  I want to ajouter un commentaire à un sondage
  So that je puisse partager mes réflexions

  Scenario: Ajouter un commentaire valide
    Given un sondage existe avec l'ID "1"
    When j'envoie une requête POST vers "/api/sondage/1/commentaires" avec le corps suivant:
      """
      {
        "content": "Super sondage !"
      }
      """
    Then la réponse a un statut HTTP 201 (Created)
    And la réponse contient un ID unique pour le commentaire

  Scenario: Ajouter un commentaire à un sondage inexistant
    Given aucun sondage n'existe avec l'ID "999"
    When j'envoie une requête POST vers "/api/sondage/999/commentaires" avec le corps suivant:
    """
    {
      "content": "Super sondage !"
    }
    """
    Then la réponse a un statut HTTP 404 (Not Found)
    And la réponse contient le message "Sondage non trouvé"

  Scenario: Ajouter un commentaire avec un contenu vide
    Given un sondage existe avec l'ID "1"
    When j'envoie une requête POST vers "/api/sondage/1/commentaires" avec le corps suivant:
    """
    {
      "content": ""
    }
    """
    Then la réponse a un statut HTTP 400 (Bad Request)
    And la réponse contient le message "Le contenu du commentaire est requis"

  Scenario: Ajouter un commentaire sans fournir de corps de requête
    Given un sondage existe avec l'ID "1"
    When j'envoie une requête POST vide vers "/api/sondage/1/commentaires"
    Then la réponse a un statut HTTP 400 (Bad Request)
    And la réponse contient le message "Le corps de la requête est invalide ou manquant"

  Scenario: Serveur inaccessible lors de l'ajout d'un commentaire
    Given le serveur est inaccessible
    When j'envoie une requête POST vers "/api/sondage/1/commentaires" avec le corps suivant:
    """
    {
      "content": "Super sondage !"
    }
    """
    Then la réponse a un statut HTTP 503 (Service Unavailable)
    And la réponse contient le message "Le service est temporairement indisponible"

  Scenario: Ajouter un commentaire avec un contenu trop long
    Given un sondage existe avec l'ID "1"
    When j'envoie une requête POST vers "/api/sondage/1/commentaires" avec le corps suivant:
    """
    {
      "content": "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur."
    }
    """
    Then la réponse a un statut HTTP 400 (Bad Request)
    And la réponse contient le message "Le contenu du commentaire dépasse la limite autorisée"