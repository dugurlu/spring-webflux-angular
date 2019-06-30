package contracts

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description('should return all projects')

    request {
        method GET()
        url '/api/v1/projects'
    }
    response {
        status OK()
        headers {
            contentType applicationJsonUtf8()
        }
        body("""
            [
                {"id": "1", "name": "test-project"}
            ]
        """)

    }
}
