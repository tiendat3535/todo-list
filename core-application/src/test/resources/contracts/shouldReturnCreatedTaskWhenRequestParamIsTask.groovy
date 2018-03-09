import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description "should return created Task when request for creating a Task"
    request {
        method POST()
        headers {
            contentType(applicationJson())
        }
        url "/v1/private/todos"
        body (file("sendTask.json"))
    }
    response {
        status 201
        headers {
            contentType(applicationJson())
        }
        body (file("createdTask.json"))
    }
}